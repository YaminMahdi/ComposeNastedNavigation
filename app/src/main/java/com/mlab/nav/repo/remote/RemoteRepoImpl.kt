package com.mlab.nav.repo.remote

import com.mlab.nav.di.IoDispatcher
import com.compose.friendship.model.UserInfo
import com.mlab.nav.RequestState
import com.mlab.nav.repo.UserRepo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.http.HttpMethod
import io.ktor.http.parameters
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import io.ktor.http.isSuccess as isSuccess1

class RemoteRepoImpl @Inject constructor(
    @IoDispatcher private val io: CoroutineDispatcher,
    private val httpClient: HttpClient
    ) : UserRepo {

    override suspend fun create(
        name: String,
        email: String,
        gender: String,
        status: String
    ): RequestState<UserInfo> {
        return withContext(io) {
            try {
                // @FormUrlEncoded - alternate submitForm
                val result = httpClient.submitForm(
                    url = "public/v2/users",
                    formParameters = parameters {
                        append(name = "name", value = name)
                        append(name = "email", value = email)
                        append(name = "gender", value = gender)
                        append(name = "status", value = status)
                    }
                )
                if (result.status.isSuccess1()) {
                    val data = result.body<UserInfo>()
                    RequestState.Success(data)
                } else {
                    RequestState.Error(result.status.value.toString())
                }
            } catch (e: Exception) {
                RequestState.Error(e.message.toString())
            }
        }
    }

    override suspend fun update(
        userId: String,
        name: String,
        email: String,
        gender: String,
        status: String
    ): RequestState<UserInfo> {
        return withContext(io) {
            try {
                // @FormUrlEncoded - alternate submitForm
                val result = httpClient.submitForm(url = "public/v2/users/$userId",
                    formParameters = parameters {
                        append(name = "name", value = name)
                        append(name = "email", value = email)
                        append(name = "gender", value = gender)
                        append(name = "status", value = status)
                    }) {
                    method = HttpMethod.Patch
                }
                if (result.status.isSuccess1()) {
                    val data = result.body<UserInfo>()
                    RequestState.Success(data)
                } else {
                    RequestState.Error(result.status.value.toString())
                }
            } catch (e: Exception) {
                RequestState.Error(e.message.toString())
            }
        }
    }

    override suspend fun getUsers(): RequestState<List<UserInfo>> {
        return withContext(io) {
            try {
                val result = httpClient.get("public/v2/users")
                if (result.status.isSuccess1()) {
                    val data = result.body<List<UserInfo>>()
                    RequestState.Success(data)
                } else {
                    RequestState.Error(result.status.value.toString())
                }
            } catch (e: Exception) {
                RequestState.Error(e.message.toString())
            }
        }
    }

}