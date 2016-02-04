package ru.redenergy.report.web.routes

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.apache.commons.codec.digest.DigestUtils
import ru.redenergy.report.web.QReportApplication
import ru.redenergy.report.web.entities.AccessLevel
import ru.redenergy.report.web.entities.User
import ru.redenergy.report.web.response.StatusResponse
import spark.Request
import spark.Response
import spark.Route
import java.util.*

class AddUserRoute(val app: QReportApplication): Route {

    override fun handle(request: Request, response: Response): Any? {
        val requester = app.findUserByAccessToken(request.cookie("access_token")) ?: return StatusResponse(false)
        if(requester.level != AccessLevel.MASTER) return StatusResponse(false)

        val login = request.queryParams("login")
        val password = request.queryParams("password")
        val level = AccessLevel.valueOf(request.queryParams("level"))
        val fullServerAccess = request.queryParams("fullServerAccess").toBoolean()
        val serverPermissions = Gson().fromJson<List<String>>(request.queryParams("servers"), object : TypeToken<List<String>>(){}.rawType)

        if(app.userDao.queryBuilder().where().eq("username", login) != null) {
            val user = User(UUID.randomUUID(), login, level, fullServerAccess, serverPermissions, DigestUtils.sha256Hex(password), "")
            app.userDao.create(user)
            return StatusResponse(true)
        } else {
            return StatusResponse(false)
        }
    }

}
