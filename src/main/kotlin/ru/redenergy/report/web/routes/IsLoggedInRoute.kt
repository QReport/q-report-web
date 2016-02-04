package ru.redenergy.report.web.routes

import ru.redenergy.report.web.QReportApplication
import ru.redenergy.report.web.response.StatusResponse
import spark.Request
import spark.Response
import spark.Route

class IsLoggedInRoute(val app: QReportApplication): Route {

    override fun handle(request: Request, response: Response): Any? {
        val accessToken: String? = request.cookie("access_token")
        if(accessToken != null){
            val user = app.findUserByAccessToken(accessToken)
            return StatusResponse(true, user)
        } else {
            return StatusResponse(false)
        }
    }

}