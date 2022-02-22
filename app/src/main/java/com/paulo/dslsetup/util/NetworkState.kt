package com.paulo.dslsetup.util

import com.paulo.dslsetup.data.repository.server.Todo
import java.lang.Exception

sealed class NetworkState {
    data class Success(val data : List<Todo>) : NetworkState()
    object InvalidData : NetworkState()
    data class Error(val error : String) : NetworkState()
    data class NetworkException(val error : String) : NetworkState()
    sealed class HttpErrors : NetworkState() {
        data class ResourceForbidden(val exception: String) : HttpErrors()
        data class ResourceNotFound(val exception: String) : HttpErrors()
        data class InternalServerError(val exception: String) : HttpErrors()
        data class BadGateWay(val exception: String) : HttpErrors()
        data class ResourceRemoved(val exception: String) : HttpErrors()
        data class RemovedResourceFound(val exception: String) : HttpErrors()
    }
}
