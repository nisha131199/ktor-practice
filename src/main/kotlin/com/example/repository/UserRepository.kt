package com.example.repository

import com.example.dto.User
import com.example.entity.Users
import com.example.entity.Users.toUserDto
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object UserRepository {
    fun createUser(name: String, email: String, phone: String, password: String): Int {
        return transaction {
            Users.insert {
                it[this.name] = name
                it[this.email] = email
                it[this.phone] = phone
                it[this.password] = password
            } get Users.id
        }
    }

    fun login(phone: String, password: String): User? {
        return transaction {
            Users.select { (Users.phone eq phone) and (Users.password eq password) }.firstOrNull()?.toUserDto()
        }
    }

    fun getUserDetails(id: Int): User? {
        return transaction {
            Users.select { Users.id eq id }.firstOrNull()?.toUserDto()
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