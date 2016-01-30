package ru.redenergy.report.web.routes

import ru.redenergy.report.web.QReportApplication
import ru.redenergy.report.web.response.StatusResponse
import spark.Request
import spark.Response
import spark.Route

class IsLoggedInRoute(val app: QReportApplication): Route {

    override fun handle(request: Request, response: Response): Any? {
        val accessToken: String? = request.cookie("access_token")
        return StatusResponse((accessToken != null && app.isAccessTokenValid(accessToken)))
    }

}