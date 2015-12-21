package ru.redenergy.report.web.routes

import ru.redenergy.report.web.QReportApplication
import spark.ModelAndView
import spark.Request
import spark.Response
import spark.TemplateViewRoute

class ReportsListRoute(val app: QReportApplication): TemplateViewRoute {

    override fun handle(request: Request, response: Response): ModelAndView? {
        return ModelAndView(mapOf(Pair("tickets", app.ticketDao.queryForAll())), "reports_list")
    }
}