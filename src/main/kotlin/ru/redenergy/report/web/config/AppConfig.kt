package ru.redenergy.report.web.config

class AppConfig(override val databasePath: String, override val databaseLogin: String, override val databasePassword: String, override val port: Int = 4567): IAppConfig