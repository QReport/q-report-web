package ru.redenergy.report.web.routes

import com.google.gson.Gson
import com.google.gson.JsonObject
import ru.redenergy.report.common.TicketReason
import ru.redenergy.report.common.entity.Ticket
import ru.redenergy.report.web.QReportApplication
import ru.redenergy.report.web.response.StatusResponse
import spark.Request
import spark.Response
import spark.Route

public class StatsRoute(val app: QReportApplication): Route{

    override fun handle(request: Request, response: Response): Any? {
        val tickets = app.ticketDao.queryForAll()
        val result = JsonObject().apply {
            add("tickets", Gson().toJsonTree(countTickets(tickets)))
            add("users", Gson().toJsonTree(getActiveUsers(tickets)))
            addProperty("average", calculateAverageResponseTime(tickets))
        }
        return StatusResponse(true, result)
    }

    fun countTickets(tickets: MutableList<Ticket>): Map<TicketReason, Int> =
            mapOf(*TicketReason.values
                    .map { r -> Pair(r, tickets.count { it.reason.equals(r) })}
                    .toTypedArray())


    fun getActiveUsers(tickets: MutableList<Ticket>): Map<String, Int> =
            linkedMapOf(*tickets
                    .map { it.sender }
                    .map { Pair(it, tickets.count { t -> t.sender.equals(it) }) }
                    .toTypedArray())

    fun calculateAverageResponseTime(tickets: MutableList<Ticket>): Long =
            tickets.filter { t -> t.messages.any { m -> m.sender != t.sender } }
                    .map { it.messages.first { m -> m.sender != it.sender } .timestamp - it.messages[0].timestamp }
                    .run { return sum() / count() }


}
