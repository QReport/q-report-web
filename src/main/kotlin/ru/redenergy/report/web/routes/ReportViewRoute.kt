package ru.redenergy.report.web.routes

import ru.redenergy.report.web.QReportApplication
import ru.redenergy.report.web.response.StatusResponse
import spark.Request
import spark.Response
import spark.Route
import java.util.*

class ReportViewRoute(val app: QReportApplication): Route {
    override fun handle(request: Request, response: Response): Any? {
        val uid = UUID.fromString(request.params("id"))
        val ticket = app.ticketDao.queryBuilder().where().eq("uid", uid).queryForFirst()
        return StatusResponse(true, ticket)
    }
}