package ru.redenergy.report.web

import ru.redenergy.report.web.config.StandaloneConfig

object Starter {
    lateinit var app: QReportApplication
}

fun main(args: Array<String>) {
    val config = StandaloneConfig("jdbc:mysql://localhost:3306/qreport", "root", "mysql")
    Starter.app = QReportApplication(config)
    Starter.app.start(args)
}