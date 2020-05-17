package com.zestworks.woebotapplication.repository

import android.app.Application
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import java.io.FileNotFoundException
import java.io.IOException

@UnstableDefault
class InMemCachedRepository(private val application: Application) :
    Repository {
    private val dataMap: HashMap<String, WoebotNetworkModel> = hashMapOf()
    override fun getStartResponse(): RepositoryResponse {
        return try {
            if (dataMap.isEmpty()) {
                invalidateCache()
            }
            RepositoryResponse.Success(dataMap.values.first { it.isStart() })
        } catch (ex: FileNotFoundException) {
            RepositoryResponse.Error("FileNotFound - exception message :${ex.message}")
        } catch (ex: IOException) {
            RepositoryResponse.Error("IOException - exception message : ${ex.message}")
        }
    }

    override fun getResponseModelForRoute(routeID: String): RepositoryResponse {
        //here, payload can be sent to server.
        if (dataMap.isEmpty()) {
            invalidateCache()
        }

        return when (val model = dataMap[routeID]) {
            null -> RepositoryResponse.Error("Unable to fetch data ")
            else -> RepositoryResponse.Success(model)
        }
    }

    private fun invalidateCache() {
        val inputStream = application.assets.open("allornothing.json")
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        String(buffer).let {
            Json.parseJson(it).jsonObject.values.forEach { jsonElement ->
                val model = Json.fromJson(WoebotNetworkModel.serializer(), jsonElement)
                dataMap[model.id] = model
            }
        }
    }
}

sealed class RepositoryResponse {
    data class Success(val woebotNetworkModel: WoebotNetworkModel) : RepositoryResponse()
    data class Error(val reason: String) : RepositoryResponse()
}