package com.teammeditalk.medicationproject.data.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.teammeditalk.medicationproject.UserHealthInfo
import java.io.InputStream
import java.io.OutputStream

object UserHealthInfoSerializer : Serializer<UserHealthInfo> {
    override val defaultValue: UserHealthInfo = UserHealthInfo.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserHealthInfo {
        try {
            return UserHealthInfo.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto", e)
        }
    }

    override suspend fun writeTo(
        t: UserHealthInfo,
        output: OutputStream,
    ) {
        t.writeTo(output)
    }
}
