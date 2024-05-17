package com.mlab.nav.presentation

import androidx.annotation.IdRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mlab.nav.RequestState
import com.mlab.nav.model.UserInfo
import com.mlab.nav.navigation.MainScreenType
import com.mlab.nav.repo.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val remoteRepo: UserRepo,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    var currentBottomNavDestination  = savedStateHandle.getStateFlow("currentBottomNavDestination", MainScreenType.Home.name)

    fun changeBottomNavDestination(value: String) {
            savedStateHandle["currentBottomNavDestination"] = value
   }

    private val _usersCopy = MutableStateFlow(listOf<UserInfo>())
    private val _users = MutableStateFlow(listOf<UserInfo>())
    val users = _users.asStateFlow()

    private val _getUserState = MutableSharedFlow<RequestState<List<UserInfo>>>()
    val getUserState = _getUserState.asSharedFlow()

    fun changeButton(@IdRes id: Int) {
        savedStateHandle["selectedButton"] = id
    }

    fun filterUser(status: String) {
        val filteredData = _usersCopy.value.filter { it.status == status }
        _users.update { filteredData }

    }

    var selectedTab
        get() = savedStateHandle.get<Int>("selectedTab") ?: 0
        set(value) {
            savedStateHandle["selectedTab"] = value
        }

    fun getUsers() {
        viewModelScope.launch {
            _getUserState.emit(RequestState.Loading)
            val result = remoteRepo.getUsers()
            if (result is RequestState.Success) {
                _usersCopy.update { result.data }
                filterUser(if (selectedTab == 0) "active" else "inactive")
            }
            _getUserState.emit(result)
        }
    }

    fun createUser(
        name: String,
        email: String,
        gender: String,
        status: String,
        data: (RequestState<UserInfo>) -> Unit,
    ) {

        viewModelScope.launch {
            val result =
                remoteRepo.create(name = name, email = email, gender = gender, status = status)
            data.invoke(result)
        }
    }

    fun updateUser(
        userId: String,
        name: String,
        email: String,
        gender: String,
        status: String,
        data: (RequestState<UserInfo>) -> Unit,
    ) {
        viewModelScope.launch {
            val result =
                remoteRepo.update(
                    userId = userId,
                    name = name,
                    email = email,
                    gender = gender,
                    status = status
                )
            data.invoke(result)
        }
    }
}