package ru.redenergy.report.web.routes

import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.lang3.RandomStringUtils
import ru.redenergy.report.web.QReportApplication
import ru.redenergy.report.web.entities.User
import ru.redenergy.report.web.response.StatusResponse
import spark.Request
import spark.Response
import spark.Route

class AuthUserRoute(val app: QReportApplication): Route {

    override fun handle(request: Request, response: Response): Any {
        val login = request.queryParams("login")
        val password = request.queryParams("password")
        val hashedPassword = DigestUtils.sha256Hex(password)

        val user = app.userDao.queryBuilder()
                .where()
                .eq("username", login)
                .and()
                .eq("passwordHash", hashedPassword).queryForFirst()
                ?: return StatusResponse(false, null);

        val accessToken = DigestUtils.md5Hex(RandomStringUtils.random(30))

        user.accessToken = accessToken
        app.userDao.update(user)

        response.cookie("access_token", accessToken)

        return StatusResponse(true, mapOf(Pair("access_token", accessToken)))

    }

}