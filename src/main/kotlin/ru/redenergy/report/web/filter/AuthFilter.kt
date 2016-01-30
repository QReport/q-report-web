package ru.redenergy.report.web.filter

import ru.redenergy.report.web.QReportApplication
import ru.redenergy.report.web.entities.User
import ru.redenergy.report.web.exception.NotAuthorizedException
import spark.Filter
import spark.Request
import spark.Response

class AuthFilter(val app: QReportApplication): Filter {

    override fun handle(request: Request, response: Response) {
        val accessToken: String? = request.cookie("access_token")

        if(!(accessToken != null && app.isAccessTokenValid(accessToken))) {
            throw NotAuthorizedException("${request.host()} tried to access ${request.url()} while not being authorized")
        }

    }

}

