package com.teammeditalk.medicationproject.data.model.search

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.serialization.Serializable

@Xml(name = "response")
@Serializable
data class SearchDiseaseResponse(
    @Element(name = "header")
    val header: DiseaseHeader,
    @Element(name = "body")
    val body: DiseaseBody,
)

@Xml(name = "header")
@Serializable
data class DiseaseHeader(
    @PropertyElement
    val resultCode: String,
    @PropertyElement
    val resultMsg: String,
)

@Xml(name = "body")
@Serializable
data class DiseaseBody(
    @PropertyElement
    val pageNo: Int,
    @PropertyElement
    val totalCount: Int,
    @Element(name = "items")
    val items: DiseaseItems, // 여기가 변경되었습니다!
    @PropertyElement
    val numOfRows: Int,
)

@Xml(name = "items")
@Serializable
data class DiseaseItems(
    @Element(name = "item")
    val itemList: List<DiseaseItem> = emptyList(),
)

@Xml(name = "item")
@Serializable
data class DiseaseItem(
    @PropertyElement
    val sickNm: String,
    @PropertyElement
    val sickCd: String,
    @PropertyElement
    val sickEngNm: String,
)
