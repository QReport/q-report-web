package ru.redenergy.report.web.filter

import ru.redenergy.report.web.QReportApplication
import ru.redenergy.report.web.entities.User
import spark.Filter
import spark.Request
import spark.Response

class AuthFilter(val app: QReportApplication): Filter {

    override fun handle(request: Request, response: Response) {
        var success = false
        val accessToken: String? = request.cookie("access_token")

        if(accessToken != null) {
            val user: User? = app.userDao.queryBuilder().where().eq("accessToken", accessToken).queryForFirst()
            if(user != null)
                success = true
        }

        if(!success)
            response.redirect("/auth")
    }

}

