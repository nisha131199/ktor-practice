package com.example.entity

import com.example.dto.User
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

object Users: Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val name: Column<String> = varchar("name", 100)
    val email: Column<String> = varchar("email", 100).uniqueIndex()
    val phone: Column<String> = varchar("phone", 100).uniqueIndex()
    val password: Column<String> = varchar("password", 100)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id)

    fun ResultRow.toUserDto(): User {
        return User(
            id = this[id],
            name = this[name],
            email = this[email],
            phone = this[phone],
            password = this[password]
        )
    }

    fun Iterable<User>.asUsers() = this.map { it }
}