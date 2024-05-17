package com.mlab.nav.repo

import com.mlab.nav.RequestState
import com.mlab.nav.model.UserInfo

interface UserRepo {

    suspend fun create(
        name: String,
        email: String,
        gender: String,
        status: String
    ): RequestState<UserInfo>

    suspend fun update(
        userId: String,
        name: String,
        email: String,
        gender: String,
        status: String
    ): RequestState<UserInfo>

    suspend fun getUsers(): RequestState<List<UserInfo>>
}