package ru.redenergy.report.web

import com.j256.ormlite.dao.Dao
import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.field.DataPersisterManager
import com.j256.ormlite.field.DatabaseFieldConfig
import com.j256.ormlite.jdbc.JdbcConnectionSource
import com.j256.ormlite.table.DatabaseTableConfig
import com.j256.ormlite.table.TableUtils
import org.apache.commons.codec.digest.DigestUtils
import ru.redenergy.report.common.entity.Ticket
import ru.redenergy.report.web.config.IAppConfig
import ru.redenergy.report.web.entities.AccessLevel
import ru.redenergy.report.web.entities.User
import ru.redenergy.report.web.exception.NotAuthorizedException
import ru.redenergy.report.web.filter.AuthFilter
import ru.redenergy.report.web.orm.JsonPersister
import ru.redenergy.report.web.response.transformer.JsonTransformer
import ru.redenergy.report.web.routes.*
import spark.Spark
import java.util.*

class QReportApplication(val config: IAppConfig) {

    init{
        DataPersisterManager.registerDataPersisters(JsonPersister.getSingleton())
    }

    var ticketConfig = DatabaseTableConfig(Ticket::class.java, arrayListOf<DatabaseFieldConfig>().apply {
        add(DatabaseFieldConfig("uid").apply { isGeneratedId = true })
        add(DatabaseFieldConfig("status"))
        add(DatabaseFieldConfig("sender"))
        add(DatabaseFieldConfig("server"))
        add(DatabaseFieldConfig("reason"))
        add(DatabaseFieldConfig("messages").apply { persisterClass = JsonPersister::class.java })
    }).apply { tableName = "tickets" }

    var connectionSource = JdbcConnectionSource(config.databasePath, config.databaseLogin, config.databasePassword)

    var ticketDao = DaoManager.createDao<Dao<Ticket, Int>, Ticket>(connectionSource, ticketConfig)

    var userDao = DaoManager.createDao<Dao<User, UUID>, User>(connectionSource, User::class.java)


    fun start() {
        registerRoutes()
        TableUtils.createTableIfNotExists(connectionSource, Ticket::class.java)

        if(!userDao.isTableExists) {
            TableUtils.createTableIfNotExists(connectionSource, User::class.java)
            userDao.create(User(UUID.randomUUID(), "root", AccessLevel.MASTER, true, arrayListOf<String>(), DigestUtils.sha256Hex("rainbow"), ""))
        }
    }

    fun stop(){
        Spark.stop()
    }

    fun registerRoutes(){
        Spark.port(config.port)

        Spark.staticFileLocation("/public")

        Spark.before("/admin/*", AuthFilter(this))

        Spark.get("/admin/reports", ReportsListRoute(this), JsonTransformer())
        Spark.get("/admin/reports/:id", ReportViewRoute(this), JsonTransformer())
        Spark.get("/admin/stats", StatsRoute(this), JsonTransformer())
        Spark.get("/admin/users", UsersInfoRoute(this), JsonTransformer())
        Spark.get("/isLoggedIn", IsLoggedInRoute(this), JsonTransformer())

        Spark.post("/auth", AuthUserRoute(this), JsonTransformer())
        Spark.post("/logout", LogOutRoute(this), JsonTransformer())

        Spark.post("/admin/users/add", AddUserRoute(this), JsonTransformer())
        Spark.post("/admin/reports/:id/addMessage", AddMessageRoute(this), JsonTransformer())
        Spark.post("/admin/reports/:id/updateStatus", UpdateStatusRoute(this), JsonTransformer())

        Spark.post("/admin/users/update", UpdatePasswordRoute(this), JsonTransformer())

        Spark.delete("/admin/users/:user", DeleteUserRoute(this), JsonTransformer())

        Spark.exception(NotAuthorizedException::class.java, {e, req, res ->
            println(e.message)
            res.status(401)
        })
    }

    fun findUserByAccessToken(accessToken: String): User? =
            userDao.queryBuilder().where().eq("accessToken", accessToken).queryForFirst()

    /**
     * Checks if given access token is valid and relates to any of users
     */
    fun isAccessTokenValid(accessToken: String): Boolean =
            userDao.queryBuilder().where().eq("accessToken", accessToken).queryForFirst() != null

}