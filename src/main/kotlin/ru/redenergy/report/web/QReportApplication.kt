package ru.redenergy.report.web

import com.google.gson.Gson
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.field.DataPersisterManager
import com.j256.ormlite.jdbc.JdbcConnectionSource
import com.j256.ormlite.table.TableUtils
import de.neuland.jade4j.JadeConfiguration
import ru.redenergy.report.web.config.IAppConfig
import ru.redenergy.report.web.entities.Ticket
import ru.redenergy.report.web.entities.User
import ru.redenergy.report.web.exception.NotAuthorizedException
import ru.redenergy.report.web.filter.AuthFilter
import ru.redenergy.report.web.orm.JsonPersister
import ru.redenergy.report.web.response.transformer.JsonTransformer
import ru.redenergy.report.web.routes.AuthRoute
import ru.redenergy.report.web.routes.ReportsListRoute
import ru.redenergy.report.web.routes.ViewReportRoute
import ru.redenergy.report.web.routes.back.AddMessageRoute
import ru.redenergy.report.web.routes.back.AuthUserRoute
import spark.Spark
import spark.template.jade.JadeTemplateEngine
import spark.template.jade.loader.SparkClasspathTemplateLoader
import java.util.*

class QReportApplication(val config: IAppConfig) {

    init{
        DataPersisterManager.registerDataPersisters(JsonPersister.getSingleton())
    }

    var connectionSource = JdbcConnectionSource(config.databasePath, config.databaseLogin, config.databasePassword)

    var ticketDao = DaoManager.createDao<Dao<Ticket, UUID>, Ticket>(connectionSource, Ticket::class.java)

    var userDao = DaoManager.createDao<Dao<User, UUID>, User>(connectionSource, User::class.java)

    val jadeEngine = JadeTemplateEngine(JadeConfiguration().apply {
        templateLoader = SparkClasspathTemplateLoader("templates")
        isPrettyPrint = true
    })

    fun start(args: Array<String>) {
        registerRoutes()
        TableUtils.createTableIfNotExists(connectionSource, Ticket::class.java)
        TableUtils.createTableIfNotExists(connectionSource, User::class.java)
    }

    fun registerRoutes(){
        Spark.before("/admin/*", AuthFilter(this))

        Spark.get("/auth", AuthRoute(this), jadeEngine)

        Spark.get("/admin/reports", ReportsListRoute(this), jadeEngine)
        Spark.get("/admin/reports/:id", ViewReportRoute(this), jadeEngine)

        Spark.post("/auth", AuthUserRoute(this), JsonTransformer())

        Spark.post("admin/reports/:id/addMessage", AddMessageRoute(this), JsonTransformer())

        Spark.exception(NotAuthorizedException::class.java, {e, req, res -> res.redirect("/auth")})
    }

}