package com.example.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SupabaseTag(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "category") val category: String,
    @Json(name = "created_at") val createdAt: String? = null,
    @Json(name = "language") val language: String? = null
)
