package ru.redenergy.report.web.entities

/**
 * Represents user's permission on server
 *
 * @constructor
 * @param server - server name,
 * @param read - if user can view tickets from server
 * @param modify - if user can add messages and change status of ticket from server
 */
data class Permission(val server: String, val read: Boolean, val modify: Boolean)