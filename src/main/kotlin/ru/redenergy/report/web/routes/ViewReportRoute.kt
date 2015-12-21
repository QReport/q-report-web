package ru.redenergy.report.web.routes

import ru.redenergy.report.web.QReportApplication
import ru.redenergy.report.web.Starter
import spark.ModelAndView
import spark.Request
import spark.Response
import spark.TemplateViewRoute
import java.util.*

class ViewReportRoute(val app: QReportApplication): TemplateViewRoute{

    override fun handle(request: Request, response: Response): ModelAndView {
        val uid = UUID.fromString(request.params("id"))
        val ticket = app.ticketDao.queryBuilder().where().eq("uid", uid).queryForFirst()
        return ModelAndView(mapOf(Pair("ticket", ticket)), "report_view")
    }
}