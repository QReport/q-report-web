package ru.redenergy.report.web

import de.neuland.jade4j.JadeConfiguration
import spark.ModelAndView
import spark.Spark
import spark.template.jade.JadeTemplateEngine
import spark.template.jade.loader.SparkClasspathTemplateLoader

class QReportApplication {

    val jadeEngine = JadeTemplateEngine(JadeConfiguration().apply {
        templateLoader = SparkClasspathTemplateLoader("templates")
        isPrettyPrint = true
    })

    fun start(args: Array<String>) {
        registerRoutes()
    }

    fun registerRoutes(){
        Spark.get("/", { request, response ->
            ModelAndView(mapOf<Any, Any>(), "index")
        }, jadeEngine)
    }

}