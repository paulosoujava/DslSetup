package com.paulo.dslsetup.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulo.dslsetup.data.repository.server.Todo
import com.paulo.dslsetup.data.repository.server.TodoApi
import com.paulo.dslsetup.util.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val api: TodoApi
) : ViewModel() {


    private lateinit var _todos: List<Todo>


    private lateinit var _error: MutableStateFlow<String>


    private lateinit var _isShowViewEmpty: MutableStateFlow<Boolean>



    suspend fun getTodos(){

        val state = fetchTodo()
        Log.d("TODO", "HERE 1"+ state.toString())
        handleTodosResult( state )
    }

    suspend fun fetchTodo(): NetworkState {
        Log.d("TODO", "HERE 3")
        return try {
            val response = api.getTodos()
            if (response.isSuccessful) {
                if (response != null)
                    NetworkState.Success(response.body()!!)
                else
                    NetworkState.InvalidData
            } else {
                when (response.code()) {
                    403 -> NetworkState.HttpErrors.ResourceForbidden(response.message())
                    404 -> NetworkState.HttpErrors.ResourceNotFound(response.message())
                    500 -> NetworkState.HttpErrors.InternalServerError(response.message())
                    502 -> NetworkState.HttpErrors.BadGateWay(response.message())
                    301 -> NetworkState.HttpErrors.ResourceRemoved(response.message())
                    302 -> NetworkState.HttpErrors.RemovedResourceFound(response.message())
                    else -> NetworkState.Error(response.message())
                }
            }
        } catch (error: Exception) {
            NetworkState.NetworkException(error.message!!)
        }
    }

    private fun handleTodosResult(networkState: NetworkState) {
        return when(networkState) {
            is NetworkState.Success -> showTodo(networkState.data)
            is NetworkState.HttpErrors.ResourceForbidden -> handleError(networkState.exception)
            is NetworkState.HttpErrors.ResourceNotFound -> handleError(networkState.exception)
            is NetworkState.HttpErrors.InternalServerError -> handleError(networkState.exception)
            is NetworkState.HttpErrors.BadGateWay -> handleError(networkState.exception)
            is NetworkState.HttpErrors.ResourceRemoved -> handleError(networkState.exception)
            is NetworkState.HttpErrors.RemovedResourceFound -> handleError(networkState.exception)
            is NetworkState.InvalidData -> showEmptyView()
            is NetworkState.Error -> handleError(networkState.error)
            is NetworkState.NetworkException -> handleError(networkState.error)
        }
    }

    private fun showTodo(data: List<Todo>) {
        Log.d("TODO", "NOTHING")
        data.forEach {
            Log.d("TODO", it.title)
        }

    }

    private fun showEmptyView() {
            _isShowViewEmpty.value = true
    }

    private fun handleError(error: String) {

    }

    private fun showTodos(data: List<Todo>) {

    }


}