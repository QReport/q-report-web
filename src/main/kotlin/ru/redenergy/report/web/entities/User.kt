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
                var level: AccessLevel,

                @DatabaseField
                var fullServerAccess: Boolean,

                @DatabaseField(persisterClass = JsonPersister::class)
                val serverPermission:  List<String>,

                @Transient @DatabaseField
                var passwordHash: String,

                @Transient @DatabaseField
                var accessToken: String) {



    constructor(): this(UUID.randomUUID(), "", AccessLevel.GUEST, false, arrayListOf<String>(), "", "")

    fun canReadOnServer(server: String) =
            serverPermission.any { it.equals(server) }

    fun canModifyOnServer(server: String) =
            (level.equals(AccessLevel.HELPER) || level.equals(AccessLevel.MASTER))
            && serverPermission.any { it.equals(server) }
}