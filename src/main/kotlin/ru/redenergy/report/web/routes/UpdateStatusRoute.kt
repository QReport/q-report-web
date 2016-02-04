package ru.redenergy.report.web.routes

import ru.redenergy.report.common.TicketStatus
import ru.redenergy.report.web.QReportApplication
import ru.redenergy.report.web.response.StatusResponse
import spark.Request
import spark.Response
import spark.Route
import java.util.*

class UpdateStatusRoute(val app: QReportApplication): Route {

    override fun handle(request: Request, response: Response): Any? {
        val user = app.findUserByAccessToken(request.cookie("access_token")) ?: return StatusResponse(false)
        val uid = request.params("id").toInt()
        val ticket = app.ticketDao.queryForId(uid);

        if(user.fullServerAccess || user.canModifyOnServer(ticket.server)) {
            val status = TicketStatus.valueOf(request.queryParams("status"))
            ticket.status = status
            app.ticketDao.update(ticket)
            return StatusResponse(true);
        }  else {
            return StatusResponse(false)
        }
    }

}
