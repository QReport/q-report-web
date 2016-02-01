package ru.redenergy.report.web

import ru.redenergy.report.web.config.IAppConfig
import ru.redenergy.report.web.config.StandaloneConfig

object Starter {
    lateinit var app: QReportApplication
}

fun main(args: Array<String>) {
    val config = parseConfigFromArguments(args)
    Starter.app = QReportApplication(config)
    Starter.app.start(args)
}

fun parseConfigFromArguments(args: Array<String>): IAppConfig{
    var path = "jdbc:mysql://localhost:3306/qreport"
    var login = "root"
    var password = "password"
    var port = 4567

    for((i, s) in args.withIndex()){
        when(s){
            "-url" -> path = args[i + 1]
            "-login" -> login = args[i + 1]
            "-pass" -> password = args[i + 1]
            "-port" -> port = args[i + 1].toInt()
        }
    }

    return StandaloneConfig(path, login, password, port)
}

