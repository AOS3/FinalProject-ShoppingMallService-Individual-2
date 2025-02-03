package com.teammeditalk.medicationproject.data.network

import android.util.Log
import com.teammeditalk.medicationproject.data.model.DrugResponse
import com.teammeditalk.medicationproject.data.model.Item
import com.teammeditalk.medicationproject.data.model.search.SearchDiseaseResponse
import com.tickaroo.tikxml.TikXml
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.JsonConvertException
import io.ktor.serialization.kotlinx.json.json
import io.ktor.serialization.kotlinx.xml.xml
import kotlinx.serialization.SerializationException
import nl.adaptivity.xmlutil.XmlDeclMode
import nl.adaptivity.xmlutil.serialization.XML
import okio.Buffer

// ktor
// todo api를 요청할 때마다 유사한 코드를 만들어야해서 번거로움  -> 개선하기
object ApiClient {
    private val client: HttpClient =
        HttpClient(Android) {
            engine {
                connectTimeout = 100_000
                socketTimeout = 100_000
            }
            install(ContentNegotiation) {
                xml(
                    format =
                        XML {
                            xmlDeclMode = XmlDeclMode.Charset
                        },
                )
            }
            install(ContentNegotiation) {
                json()
            }
            install(Logging) {
                level = LogLevel.ALL
            }
        }

    suspend fun getDrbEasyDrugList(
        itemName: String, // 제품명
    ): List<String> =
        try {
            val response: List<String> =
                client
                    .get("http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList") {
                        contentType((ContentType.Application.Json))
                        accept(ContentType.Application.Json)
                        url {
                            parameters.append(
                                "serviceKey",
                                "D5Pn1X94hE69T8eSXEWBopXajX0xhBgzDbAEkf5CCiJL4jp2I598D4ZCP9gNsnM8tRGYfL6hKiFC5KYccYVuSA==",
                            )
                            parameters.append("itemName", itemName)
                            parameters.append("type", "json")
                        }
                    }.body<DrugResponse>()
                    .body.items
                    .map {
                        it.itemName.toString()
                    }
            response
        } catch (e: SerializationException) {
            e.printStackTrace()
            Log.e("직렬화 실패", e.message.toString())
            emptyList()
        } catch (e: JsonConvertException) {
            e.printStackTrace()
            Log.e("직렬화 실패 by Json", e.message.toString())
            emptyList()
        }

    suspend fun getDrugBySymptom(symptom: String): List<Item> =
        try {
            val response =
                client
                    .get("http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList") {
                        contentType((ContentType.Application.Json))
                        accept(ContentType.Application.Json)
                        url {
                            parameters.append(
                                "serviceKey",
                                "D5Pn1X94hE69T8eSXEWBopXajX0xhBgzDbAEkf5CCiJL4jp2I598D4ZCP9gNsnM8tRGYfL6hKiFC5KYccYVuSA==",
                            )
                            parameters.append("efcyQesitm", symptom)
                            parameters.append("type", "json")
                        }
                    }.body<DrugResponse>()
                    .body.items
            response
        } catch (e: JsonConvertException) {
            e.printStackTrace()
            emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }

    suspend fun getDrugDetailInfo(itemName: String): List<Item> =
        try {
            val response =
                client
                    .get("http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList") {
                        contentType((ContentType.Application.Json))
                        accept(ContentType.Application.Json)
                        url {
                            parameters.append(
                                "serviceKey",
                                "D5Pn1X94hE69T8eSXEWBopXajX0xhBgzDbAEkf5CCiJL4jp2I598D4ZCP9gNsnM8tRGYfL6hKiFC5KYccYVuSA==",
                            )
                            parameters.append("itemName", itemName)
                            parameters.append("type", "json")
                        }
                    }.body<DrugResponse>()
                    .body.items
            response
        } catch (e: Exception) {
            Log.d("error", e.message.toString())
            emptyList()
        }

    suspend fun searchDiseaseName(searchText: String): List<String> =
        try {
            val response =
                client
                    .get("https://apis.data.go.kr/B551182/diseaseInfoService1/getDissNameCodeList1") {
                        contentType((ContentType.Application.Xml))
                        accept(ContentType.Application.Xml)
                        url {
                            parameters.append(
                                "serviceKey",
                                "D5Pn1X94hE69T8eSXEWBopXajX0xhBgzDbAEkf5CCiJL4jp2I598D4ZCP9gNsnM8tRGYfL6hKiFC5KYccYVuSA==",
                            )
                            parameters.append("sickType", "2")
                            parameters.append("medTp", "1")
                            parameters.append("diseaseType", "SICK_NM")
                            parameters.append("searchText", searchText)
                        }
                    }.bodyAsText()

            val parser: TikXml =
                TikXml
                    .Builder()
                    .exceptionOnUnreadXml(false)
                    .build()

            val source = Buffer().writeUtf8(response)
            val result =
                parser
                    .read(source, SearchDiseaseResponse::class.java)
                    .body.items.itemList
            Log.d("api 결과", result.toString())
            val sickName = result.map { it.sickNm }
            Log.d("sick name 결과", sickName.toString())

            sickName
        } catch (e: SerializationException) {
            e.printStackTrace()
            Log.e("직렬화 실패", e.message.toString())
            emptyList()
        } catch (e: JsonConvertException) {
            e.printStackTrace()
            Log.e("직렬화 실패 by Json", e.message.toString())
            emptyList()
        }
}
