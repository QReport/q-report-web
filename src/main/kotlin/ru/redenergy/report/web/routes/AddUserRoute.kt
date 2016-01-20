package ru.redenergy.report.web.routes

import org.apache.commons.codec.digest.DigestUtils
import ru.redenergy.report.web.QReportApplication
import ru.redenergy.report.web.entities.User
import ru.redenergy.report.web.response.StatusResponse
import spark.Request
import spark.Response
import spark.Route
import java.util.*

class AddUserRoute(val app: QReportApplication): Route {

    override fun handle(request: Request, response: Response): Any? {
        val login = request.queryParams("login")
        val password = request.queryParams("password")

        if(app.userDao.queryBuilder().where().eq("username", login) != null) {
            val user = User(UUID.randomUUID(), login, DigestUtils.sha256Hex(password), "")
            app.userDao.create(user)
            return StatusResponse(true)
        } else {
            return StatusResponse(false)
        }
    }

}
