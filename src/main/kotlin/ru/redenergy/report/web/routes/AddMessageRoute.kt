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
        val user = app.findUserByAccessToken(request.cookie("access_token")) ?: return StatusResponse(false)

        val uid = request.params("id").toInt()
        val ticket = app.ticketDao.queryForId(uid)

        if(user.fullServerAccess || user.canModifyOnServer(ticket.server)) {

            val text = request.queryParams("text")
            ticket.messages.add(TicketMessage(user.username, text))
            app.ticketDao.update(ticket)
            return StatusResponse(true, null)
        } else {
            return StatusResponse(false)
        }
    }

}