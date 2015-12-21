package ru.redenergy.report.web

import com.j256.ormlite.dao.Dao
import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.field.DataPersisterManager
import com.j256.ormlite.jdbc.JdbcConnectionSource
import de.neuland.jade4j.JadeConfiguration
import ru.redenergy.report.web.entities.Ticket
import ru.redenergy.report.web.orm.JsonPersister
import ru.redenergy.report.web.routes.ReportsListRoute
import ru.redenergy.report.web.routes.ViewReportRoute
import spark.Spark
import spark.template.jade.JadeTemplateEngine
import spark.template.jade.loader.SparkClasspathTemplateLoader
import java.util.*

class QReportApplication {

    init{
        DataPersisterManager.registerDataPersisters(JsonPersister.getSingleton())
    }

    var connectionSource = JdbcConnectionSource("jdbc:mysql://localhost:3306/qreport", "root", "mysql") //TODO: make database configurable

    var ticketDao = DaoManager.createDao<Dao<Ticket, UUID>, Ticket>(connectionSource, Ticket::class.java)

    val jadeEngine = JadeTemplateEngine(JadeConfiguration().apply {
        templateLoader = SparkClasspathTemplateLoader("templates")
        isPrettyPrint = true
    })

    fun start(args: Array<String>) {
        registerRoutes()
    }

    fun registerRoutes(){
        Spark.get("/admin/reports", ReportsListRoute(this), jadeEngine)
        Spark.get("/admin/reports/:id", ViewReportRoute(this), jadeEngine)
    }

}