package ru.redenergy.report.web.entities

import com.j256.ormlite.table.DatabaseTable
import com.j256.ormlite.field.DatabaseField as db
import ru.redenergy.report.web.orm.JsonPersister
import java.util.*

/**
 * A copy of Ticket.class from QReport mod
 *
 * Represents report ticket
 * @constructor
 * @param uid - identifier of a ticket, if not provided will be generated randomly
 * @param status - current status of ticket
 * @param sender - original sender of a ticket
 * @param reason - reason of a ticket
 * @param messages - messages in ticket Note: in database this field will be persisted as json string, check out [ru.redenergy.report.server.orm.JsonPersister]
 */
@DatabaseTable(tableName = "tickets")
data class Ticket(@db(id = true) var uid: UUID = UUID.randomUUID(), @db var status: TicketStatus, @db var sender: String, @db var reason: TicketReason, @db(persisterClass = JsonPersister::class, width = 16777216) var messages: MutableList<TicketMessage>) {

    /**
     * A short representation of uuid <br>
     * In general, it just returns the first 8 symbols of uuid <br>
     * Should be used as human readable uuid, because full uuid is too long to remember
     */
    val shortUid: String
        get() = this.uid.toString().substring(0, 8)

    /**
     * Empty constructor requested by ORMLite
     */
    private constructor(): this(UUID.randomUUID(), TicketStatus.OPEN, "unknown", TicketReason.OTHER, arrayListOf())

}