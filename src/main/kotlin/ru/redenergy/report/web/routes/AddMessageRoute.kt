package ru.redenergy.report.web.routes

import ru.redenergy.report.common.entity.TicketMessage
import ru.redenergy.report.web.QReportApplication
import ru.redenergy.report.web.response.StatusResponse
import spark.Request
import spark.Response
import spark.Route
import java.util.*

class AddMessageRoute(val app: QReportApplication): Route {

    override fun handle(request: Request, response: Response): Any {
        val user = app.userDao.queryBuilder().where().eq("accessToken", request.cookie("access_token")).queryForFirst()
        val uid = UUID.fromString(request.params("id"))
        val ticket = app.ticketDao.queryForId(uid)
        val text = request.queryParams("text")
        ticket.messages.add(TicketMessage(user.username, text))
        app.ticketDao.update(ticket)
        return StatusResponse(true, null)
    }

}