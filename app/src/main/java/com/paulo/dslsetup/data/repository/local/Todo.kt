package com.paulo.dslsetup.data.repository.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Todo(
    @PrimaryKey
    val id: String,
    val description: String?,
    val isDone: Boolean
)