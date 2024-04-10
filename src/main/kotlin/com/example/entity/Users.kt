package com.example.entity

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Users: Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val name: Column<String> = varchar("name", 100)
    val phone: Column<String> = varchar("phone", 100)
    val password: Column<String> = varchar("password", 100)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id)
}