package com.mlab.nav

import androidx.navigation.CollectionNavType
import androidx.navigation.NavType
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.serializer
import kotlin.reflect.KType

@OptIn(ExperimentalSerializationApi::class)
fun <T> KSerializer<T>.generateRoutePattern(
    typeMap: Map<KType, NavType<*>> = emptyMap(),
    path: String? = null,
): String {
    val map = mutableMapOf<String, NavType<Any?>?>()
    for (i in 0 until descriptor.elementsCount) {
        val argName = descriptor.getElementName(i)
        val type = descriptor.getElementDescriptor(i).computeNavType(typeMap)
        map[argName] = type
    }
    val builder = if (path != null) {
        RouteBuilder.Pattern(path, this, map)
    } else {
        RouteBuilder.Pattern(this, map)
    }
    for (elementIndex in 0 until descriptor.elementsCount) {
        builder.addArg(elementIndex)
    }
    return builder.build()
}

@Suppress("UNCHECKED_CAST")
private fun SerialDescriptor.computeNavType(
    typeMap: Map<KType, NavType<*>>
): NavType<Any?>? {
    val customType = typeMap.keys
        .find { kType -> matchKType(kType) }
        ?.let { typeMap[it] } as? NavType<Any?>
    return customType
}

fun SerialDescriptor.matchKType(kType: KType): Boolean {
    if (this.isNullable != kType.isMarkedNullable) return false
    if (this.hashCode() != serializer(kType).descriptor.hashCode()) return false
    return true
}
internal sealed class RouteBuilder<T> private constructor() {
    /**
     * DSL to construct a route pattern
     */
    class Pattern<T> : com.mlab.nav.RouteBuilder<T> {

        private val builder: Builder<T>

        /**
         * Create a builder that builds a route pattern
         *
         * @param serializer The serializer for destination type T (class, object etc.)
         * to build the route for.
         * @param typeMap map of destination arguments' name to its respective [NavType]
         */
        constructor(
            serializer: KSerializer<T>,
            typeMap: Map<String, NavType<Any?>?>
        ) : super() {
            builder = Builder(serializer, typeMap)
        }

        /**
         * Create a builder that builds a route pattern
         *
         * @param path The base uri path to which arguments are appended
         * @param serializer The serializer for destination type T (class, object etc.)
         * to build the route for.
         * @param typeMap map of destination arguments' name to its respective [NavType]
         */
        constructor(
            path: String,
            serializer: KSerializer<T>,
            typeMap: Map<String, NavType<Any?>?>
        ) : super() {
            builder = Builder(path, serializer, typeMap)
        }

        fun addArg(elementIndex: Int) {
            builder.apply(elementIndex) { name, _, paramType ->
                when (paramType) {
                    ParamType.PATH -> addPath("{$name}")
                    ParamType.QUERY -> addQuery(name, "{$name}")
                }
            }
        }

        fun build(): String = builder.build()
    }

    /**
     * Builds a route filled with argument values
     *
     * @param serializer The serializer for destination instance that you
     * need to build the route for.
     *
     * @param typeMap A map of argument name to the NavArgument of all serializable fields
     * in this destination instance
     */
    class Filled<T>(
        serializer: KSerializer<T>,
        private val typeMap: Map<String, NavType<Any?>>
    ) : com.mlab.nav.RouteBuilder<T>() {

        private val builder = Builder(serializer, typeMap)
        private var elementIndex = -1

        /**
         * Set index of the argument that is currently getting encoded
         */
        fun setElementIndex(idx: Int) {
            elementIndex = idx
        }

        /**
         * Adds argument value to the url
         */
        fun addArg(value: Any?) {
            require(!(value == null || value == "null")) {
                "Expected non-null value but got $value"
            }
            builder.apply(elementIndex) { name, type, paramType ->
                val parsedValue = if (type is CollectionNavType) {
                    type.serializeAsValues(value)
                } else {
                    listOf(type?.serializeAsValue(value))
                }
                when (paramType) {
                    ParamType.PATH -> {
                        // path arguments should be a single string value of primitive types
                        require(parsedValue.size == 1) {
                            "Expected one value for argument $name, found ${parsedValue.size}" +
                                    "values instead."
                        }
                        parsedValue.first()?.let { addPath(it) }
                    }
                    ParamType.QUERY -> parsedValue.forEach {
                        if (it != null) {
                            addQuery(name, it)
                        }
                    }
                }
            }
        }

        /**
         * Adds null value to the url
         */
        fun addNull(value: Any?) {
            require(value == null || value == "null") {
                "Expected null value but got $value"
            }
            builder.apply(elementIndex) { name, _, paramType ->
                when (paramType) {
                    ParamType.PATH -> addPath("null")
                    ParamType.QUERY -> addQuery(name, "null")
                }
            }
        }

        fun build(): String = builder.build()
    }

    enum class ParamType {
        PATH,
        QUERY
    }

    /**
     * Internal builder that generates the final url output
     */
    private class Builder<T> {
        private val serializer: KSerializer<T>
        private val typeMap: Map<String, NavType<Any?>?>
        private val path: String
        private var pathArgs = ""
        private var queryArgs = ""

        constructor(
            serializer: KSerializer<T>,
            typeMap: Map<String, NavType<Any?>?>
        ) {
            this.serializer = serializer
            this.typeMap = typeMap
            path = serializer.descriptor.serialName
        }

        constructor(
            path: String,
            serializer: KSerializer<T>,
            typeMap: Map<String, NavType<Any?>?>
        ) {
            this.serializer = serializer
            this.typeMap = typeMap
            this.path = path
        }

        /**
         * Returns final route
         */
        fun build() = path + pathArgs + queryArgs

        /**
         * Append string to the route's (url) path
         */
        fun addPath(path: String) {
            pathArgs += "/$path"
        }

        /**
         * Append string to the route's (url) query parameter
         */
        fun addQuery(name: String, value: String) {
            val symbol = if (queryArgs.isEmpty()) "?" else "&"
            queryArgs += "$symbol$name=$value"
        }

        fun apply(
            index: Int,
            block: Builder<T>.(name: String, type: NavType<Any?>?, paramType: ParamType) -> Unit
        ) {
            val descriptor = serializer.descriptor
            val elementName = descriptor.getElementName(index)
            val type = typeMap[elementName]
            val paramType = computeParamType(index, type)
            this.block(elementName, type, paramType)
        }

        /**
         * Given the descriptor of [T], computes the [ParamType] of the element (argument)
         * at [index].
         *
         * Query args if either conditions met:
         * 1. has default value
         * 2. is of [CollectionNavType]
         */
        private fun computeParamType(index: Int, type: NavType<Any?>?) =
            if (type is CollectionNavType || serializer.descriptor.isElementOptional(index)) {
                ParamType.QUERY
            } else {
                ParamType.PATH
            }
    }
}
