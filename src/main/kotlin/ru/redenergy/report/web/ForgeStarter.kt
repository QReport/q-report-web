package ru.redenergy.report.web

import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import cpw.mods.fml.common.event.FMLServerStartingEvent
import cpw.mods.fml.common.event.FMLServerStoppingEvent
import net.minecraftforge.common.config.Configuration
import ru.redenergy.report.web.config.AppConfig
import java.io.File

@Mod(modid = "qreport-web", name = "QReport Web", acceptableRemoteVersions = "*", dependencies = "required-after:qreport-server", modLanguageAdapter = "io.drakon.forge.kotlin.KotlinAdapter")
object ForgeStarter {

    var jdbcPath = "jdbc:sqlite:${File("").absolutePath}\\reports.sqlite"
    var jdbcLogin = " "
    var jdbcPassword = " "

    var port = 4567

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent){
        //using the same config which is used in server-side mod
        var config = Configuration(File(event.modConfigurationDirectory, "qreport-server.cfg"))
        jdbcPath = config.getString("jdbc", "QReport DB", jdbcPath, "JDBC database path. Currently supported: MySQL and SQLite")
        jdbcLogin = config.getString("jdbc-login", "QReport DB", jdbcLogin, "Login for database connection, leave empty if no authorization needed")
        jdbcPassword = config.getString("jdbc-password", "QReport DB", jdbcPassword, "Password for database connection, leave empty if no authorization needed")

        port = config.getInt("web-port", "QReport Web", port, 0, 65535, "Port on which QReport Web will be accessible")
        config.save()
    }

    @Mod.EventHandler
    fun serverStart(event: FMLServerStartingEvent){
        Starter.launchApplication(AppConfig(jdbcPath, jdbcLogin, jdbcPassword, port))
    }

    @Mod.EventHandler
    fun serverStop(event: FMLServerStoppingEvent){
        Starter.stopApplication()
    }


}
