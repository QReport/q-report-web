package ru.redenergy.report.web.routes

import com.google.gson.Gson
import ru.redenergy.report.common.entity.Ticket
import ru.redenergy.report.web.QReportApplication
import ru.redenergy.report.web.response.StatusResponse
import spark.Request
import spark.Response
import spark.Route

class ReportsListRoute(val app: QReportApplication): Route {

    override fun handle(request: Request, response: Response?): Any? {
        val user = app.findUserByAccessToken(request.cookie("access_token"))

        val tickets = app.ticketDao.queryForAll()
        val result = arrayListOf<Ticket>()

        if(user.fullServerAccess){
           result.addAll(tickets)
        } else {
            result.addAll(tickets.filter { user.canReadOnServer(it.server )})
        }

        return StatusResponse(true, result)
    }
}