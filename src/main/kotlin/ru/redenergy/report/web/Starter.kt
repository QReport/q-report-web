package ru.redenergy.report.web

import ru.redenergy.report.web.config.AppConfig

object Starter {
    lateinit var app: QReportApplication

    fun launchApplication(config: AppConfig){
        app = QReportApplication(config)
        app.start()
    }

    fun stopApplication(){
        app.stop()
    }

}

fun main(args: Array<String>) {
    Starter.launchApplication(parseConfigFromArguments(args))
}

fun parseConfigFromArguments(args: Array<String>): AppConfig{
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

    return AppConfig(path, login, password, port)
}

