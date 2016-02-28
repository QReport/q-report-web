package ru.redenergy.report.web.routes

import org.apache.commons.codec.digest.DigestUtils
import ru.redenergy.report.web.QReportApplication
import ru.redenergy.report.web.response.StatusResponse
import spark.Request
import spark.Response
import spark.Route

class UpdatePasswordRoute(val app: QReportApplication): Route {

    override fun handle(req: Request, res: Response): Any? {
        val user = app.findUserByAccessToken(req.cookie("access_token")) ?: return StatusResponse(false)
        val previousPassword = req.queryParams("prevPassword")
        val newPassword = req.queryParams("newPassword")
        val hashedPrevPassword = DigestUtils.sha256Hex(previousPassword)
        val foundUser = app.userDao.queryBuilder().where().eq("passwordHash", hashedPrevPassword).and().eq("username", user.username).queryForFirst()
        if(foundUser.uid.equals(user.uid)){
            foundUser.passwordHash = DigestUtils.sha256Hex(newPassword)
            app.userDao.update(foundUser)
            return StatusResponse(true)
        }
        return StatusResponse(false)
    }
}