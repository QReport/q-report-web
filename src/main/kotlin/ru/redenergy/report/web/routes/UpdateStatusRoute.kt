package ru.redenergy.report.web.routes

import ru.redenergy.report.web.QReportApplication
import ru.redenergy.report.web.entities.TicketStatus
import ru.redenergy.report.web.response.StatusResponse
import spark.Request
import spark.Response
import spark.Route
import java.util.*

class UpdateStatusRoute(val app: QReportApplication): Route {

    override fun handle(request: Request, response: Response): Any? {
        val user = app.userDao.queryBuilder().where().eq("accessToken", request.cookie("access_token")).queryForFirst() ?: return StatusResponse(false)
        val uid = UUID.fromString(request.params("id"))
        val ticket = app.ticketDao.queryForId(uid);
        val status = TicketStatus.valueOf(request.queryParams("status"))
        ticket.status = status
        app.ticketDao.update(ticket)
        return StatusResponse(true);
    }

}
