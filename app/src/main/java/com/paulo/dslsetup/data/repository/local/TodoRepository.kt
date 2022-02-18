package com.paulo.dslsetup.data.repository.local

import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun addTodo(todo: Todo)
    suspend fun deleteTodo(todo: Todo)
    suspend fun getById(id: Int):Todo
    fun getTodos():  Flow<List<Todo>>
}