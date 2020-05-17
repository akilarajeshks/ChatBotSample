package com.zestworks.woebotapplication.repository

interface Repository {
    fun getStartResponse(): RepositoryResponse
    fun getResponseModelForRoute(routeID: String): RepositoryResponse
}