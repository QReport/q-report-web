package ru.redenergy.report.web.routes

import ru.redenergy.report.web.QReportApplication
import ru.redenergy.report.web.response.StatusResponse
import spark.Request
import spark.Response
import spark.Route

class UsersInfoRoute(val app: QReportApplication): Route {

    override fun handle(request: Request?, response: Response?): Any? {
        return StatusResponse(true, app.userDao.queryForAll())
    }

}