package ru.redenergy.report.web.routes

import ru.redenergy.report.web.QReportApplication
import ru.redenergy.report.web.response.StatusResponse
import spark.Request
import spark.Response
import spark.Route

class DeleteUserRoute(val app: QReportApplication): Route {

    override fun handle(request: Request, response: Response): Any? {
        val requester = app.findUserByAccessToken(request.cookie("access_token")) ?: return StatusResponse(false)
        val toRemove = app.userDao.queryBuilder().where().eq("username", request.params("user")).query() ?: return StatusResponse(false)

        if(requester.admin){
            app.userDao.delete(toRemove)
            return StatusResponse(true)
        } else {
            return StatusResponse(false)
        }
    }
}