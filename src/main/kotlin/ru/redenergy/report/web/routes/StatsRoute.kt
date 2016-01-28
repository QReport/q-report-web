package ru.redenergy.report.web.routes

import com.google.gson.Gson
import com.google.gson.JsonObject
import ru.redenergy.report.common.entity.activeUsers
import ru.redenergy.report.common.entity.averageResponseTime
import ru.redenergy.report.common.entity.countReasons
import ru.redenergy.report.web.QReportApplication
import ru.redenergy.report.web.response.StatusResponse
import spark.Request
import spark.Response
import spark.Route

public class StatsRoute(val app: QReportApplication): Route{

    override fun handle(request: Request, response: Response): Any? {
        val tickets = app.ticketDao.queryForAll()
        val result = JsonObject().apply {
            add("tickets", Gson().toJsonTree(tickets.countReasons()))
            add("users", Gson().toJsonTree(tickets.activeUsers(5)))
            addProperty("average", tickets.averageResponseTime())
        }
        return StatusResponse(true, result)
    }

}
