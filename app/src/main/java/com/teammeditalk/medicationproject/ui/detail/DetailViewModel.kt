package com.teammeditalk.medicationproject.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.teammeditalk.medicationproject.data.model.Item
import com.teammeditalk.medicationproject.data.repository.AuthRepository
import com.teammeditalk.medicationproject.data.repository.DiseaseRepository
import com.teammeditalk.medicationproject.data.repository.MyAllergyRepository
import com.teammeditalk.medicationproject.data.repository.MyDiseaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

fun extractDiseaseKeyword(disease: String): String {
    val keyWord =
        when {
            disease.contains("간장") -> "간장질환"
            disease.contains("신장") -> "신장질환"
            disease.contains("당뇨") -> "당뇨병"
            disease.contains("갑상선") -> "갑상선질환"
            disease.contains("고혈압") -> "고혈압"
            else -> "기타"
        }
    return keyWord
}

class DetailViewModel(
    private val authRepository: AuthRepository,
    private val diseaseRepository: DiseaseRepository,
    private val myDiseaseRepository: MyDiseaseRepository,
    private val myAllergyRepository: MyAllergyRepository,
) : ViewModel() {
    private var drugInfo: Item? = null

    private var uuid: String? = null

    init {
        viewModelScope.launch {
            authRepository.uuid.collect {
                uuid = it
            }
        }
    }

    private val _myKeyWord = MutableStateFlow(emptyList<String>())
    val myKeyWord = _myKeyWord.asStateFlow()

    fun setDrugInfo(drugInfo: Item) {
        this.drugInfo = drugInfo
    }

    // todo : 장바구니에 담기  (db)
    fun saveDrugIntoCart(db: FirebaseFirestore) {
        val drug =
            hashMapOf(
                "name" to drugInfo?.itemName,
                "user_id" to uuid,
                "imageUri" to drugInfo?.itemImage,
            )

        try {
            if (uuid == null) return
            // Add a new document with a generated ID
            db
                .collection(uuid!!)
                .add(drug)
                .addOnSuccessListener { documentReference ->
                    Log.d("장바구니", "DocumentSnapshot added with ID: $documentReference")
                }.addOnFailureListener { e ->
                    Log.w("장바구니", "Error adding document", e)
                }
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            Log.d("ClassNotFoundException", "failed to save drug item into cart :${e.message}")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("Exception", "failed to save drug item into cart :${e.message}")
        }
    }

    // 내가 가진 정보 기반 키워드 찾기
    // todo : 내가 가진 지병 키워드 찾기
    fun findMyIllness() {
        val list = mutableListOf<String>()
        // 로컬에 저장된 내 지병 정보 불러오기
        viewModelScope.launch {
            myDiseaseRepository.diseaseFlow.collect { disease ->
                disease.forEach {
                    val keyWord = extractDiseaseKeyword(it)
                    println("atpnWarnQesitm : ${drugInfo?.atpnWarnQesitm}")
                    println("keyword : $keyWord")
                    if (drugInfo?.atpnWarnQesitm?.contains(keyWord) == true ||
                        drugInfo?.atpnQesitm?.contains(keyWord) == true
                    ) {
                        list.add(keyWord)
                        _myKeyWord.value = list
                    }
                }
            }
        }
    }
}
