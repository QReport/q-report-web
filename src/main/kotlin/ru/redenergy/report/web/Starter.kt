package ru.redenergy.report.web

object Starter {
    //Instance of application
    val app: QReportApplication = QReportApplication()
}

fun main(args: Array<String>) {
    Starter.app.start(args)
}