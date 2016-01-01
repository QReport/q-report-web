package ru.redenergy.report.web.routes.back

import ru.redenergy.report.web.QReportApplication
import ru.redenergy.report.web.entities.TicketMessage
import ru.redenergy.report.web.response.StatusResponse
import spark.Request
import spark.Response
import spark.Route
import java.util.*

class AddMessageRoute(val app: QReportApplication): Route {

    override fun handle(request: Request, response: Response): Any {
        val uid = UUID.fromString(request.params("id"))
        val ticket = app.ticketDao.queryForId(uid)
        val text = request.queryParams("text")
        ticket.messages.add(TicketMessage("Unknown Sender", text))
        app.ticketDao.update(ticket)
        return StatusResponse(true, null)
    }

}