package com.negahpay.sokkan.framework.net

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReturnNet<T>(
    @Json(name = "returncode")
    val returnCode: Int,
    @Json(name = "returndescription")
    val returnDescription: String,
    @Json(name = "returnAffectedObject")
    val returnAffectedObject: T?,
    @Json(name = "returnvalue")
    val returnValue: String?
)