package com.example.db

import com.example.entity.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseConfig {
    fun init() {
        Database.connect(
            url = "jdbc:mysql://sql.freedb.tech:3306/freedb_todo-database",
            driver = "com.mysql.cj.jdbc.Driver",
            user = "freedb_todo-user",
            password = "7?Cw2\$D?aMcHt2y"
        )
    }

    fun initTable() {
        transaction {
            SchemaUtils.createMissingTablesAndColumns(
                Users
            )
        }
    }
}