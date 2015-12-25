package ru.redenergy.report.web.response.transformer

import com.google.gson.Gson
import spark.ResponseTransformer

class JsonTransformer(): ResponseTransformer {

    val gson = Gson()

    override fun render(model: Any?): String? {
        return gson.toJson(model)
    }

}
