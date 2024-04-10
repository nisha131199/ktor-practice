package com.example.repository

import com.example.dto.User
import com.example.entity.Users
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object UserRepository {
    fun createUser(name: String, phone: String, password: String): Int {
        return transaction {
            Users.insert {
                it[this.name] = name
                it[this.phone] = phone
                it[this.password] = password
            } get Users.id
        }
    }

    fun login(phone: String, password: String): User? {
        return transaction {
            Users.select {
                (Users.phone eq phone) and (Users.password eq password)
            }.map {
                User(
                    it[Users.id],
                    it[Users.name],
                    it[Users.phone],
                    it[Users.password]
                )
            }.singleOrNull()
        }
    }

    fun getUserDetails(id: Int): User? {
        return transaction {
            Users.select { Users.id eq id }.map {
                User(
                    it[Users.id],
                    it[Users.name],
                    it[Users.phone],
                    it[Users.password],
                )
            }.singleOrNull()
        }
    }

    fun deleteUser(id: Int): Boolean {
        return transaction {
            Users.deleteWhere {
                this.id eq id
            } > 0
        }
    }
}