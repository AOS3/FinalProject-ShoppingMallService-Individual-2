package com.teammeditalk.medicationproject.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

object HttpRequest {
    suspend fun main() {
        val client: HttpClient =
            HttpClient(Android) {
                engine {
                    connectTimeout = 100_000
                    socketTimeout = 100_000
                }
            }
        val response: HttpResponse = client.get("https://apis.data.go.kr/B551182/diseaseInfoService1/getDissNameCodeList1")
        println(response)
        client.close()
    }
}
