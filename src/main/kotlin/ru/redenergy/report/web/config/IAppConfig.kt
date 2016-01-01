package ru.redenergy.report.web.config

/**
 * Since our application can be run and as minecraft mod and as standalone application we need a source of values for it
 */
interface IAppConfig {
    val databasePath: String

    val databaseLogin: String

    val databasePassword: String
}