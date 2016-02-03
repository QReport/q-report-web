package ru.redenergy.report.web.entities

import com.j256.ormlite.table.DatabaseTable
import ru.redenergy.report.web.orm.JsonPersister
import java.util.*
import com.j256.ormlite.field.DatabaseField

@DatabaseTable(tableName = "users")

data class User(@DatabaseField(id = true)
                var uid: UUID,

                @DatabaseField
                var username: String,

                @DatabaseField
                var admin: Boolean,

                @DatabaseField
                var fullServerAccess: Boolean,

                @DatabaseField(persisterClass = JsonPersister::class)
                val serverPermission:  List<Permission>,

                @Transient @DatabaseField
                var passwordHash: String,

                @Transient @DatabaseField
                var accessToken: String) {



    constructor(): this(UUID.randomUUID(), "", false, false, arrayListOf<Permission>(), "", "")

    fun canReadOnServer(server: String) = serverPermission.firstOrNull { it.server.equals(server, true) }?.read ?: false

    fun canModifyOnServer(server: String) = serverPermission.firstOrNull { it.server.equals(server, true)}?.modify ?: false
}