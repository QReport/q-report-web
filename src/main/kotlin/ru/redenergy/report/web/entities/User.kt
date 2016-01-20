package ru.redenergy.report.web.entities

import com.j256.ormlite.table.DatabaseTable
import java.util.*
import com.j256.ormlite.field.DatabaseField as db

@DatabaseTable(tableName = "users")
data class User(@db(id = true) var uid: UUID, @db var username: String, @Transient @db var passwordHash: String, @Transient @db var accessToken: String) {
    constructor(): this(UUID.randomUUID(), "", "", "")
}