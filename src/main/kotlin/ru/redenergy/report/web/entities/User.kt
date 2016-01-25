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
                var editUsers: Boolean,

                @DatabaseField
                var fullServerAccess: Boolean,

                @DatabaseField(persisterClass = JsonPersister::class)
                val serverPermission:  List<Permission>,

                @Transient @DatabaseField
                var passwordHash: String,

                @Transient @DatabaseField
                var accessToken: String) {



    constructor(): this(UUID.randomUUID(), "", false, false, arrayListOf<Permission>(), "", "")
}