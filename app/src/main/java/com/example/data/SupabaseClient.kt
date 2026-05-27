package com.example.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Header

@com.squareup.moshi.JsonClass(generateAdapter = true)
data class SupabaseEvent(
    @com.squareup.moshi.Json(name = "title") val title: String,
    @com.squareup.moshi.Json(name = "description") val description: String?,
    @com.squareup.moshi.Json(name = "location") val location: String?,
    @com.squareup.moshi.Json(name = "price") val price: Double = 0.0,
    @com.squareup.moshi.Json(name = "date") val date: String, // e.g. "2026-05-25"
    @com.squareup.moshi.Json(name = "time") val time: String, // e.g. "18:00:00"
    @com.squareup.moshi.Json(name = "image_url") val imageUrl: String?,
    @com.squareup.moshi.Json(name = "organizer_id") val organizerId: String? = null,
    @com.squareup.moshi.Json(name = "max_capacity") val maxCapacity: Int? = 100,
    @com.squareup.moshi.Json(name = "currency_code") val currencyCode: String = "USD"
)

@com.squareup.moshi.JsonClass(generateAdapter = true)
data class SupabaseEventResponse(
    @com.squareup.moshi.Json(name = "id") val id: String,
    @com.squareup.moshi.Json(name = "title") val title: String,
    @com.squareup.moshi.Json(name = "description") val description: String?,
    @com.squareup.moshi.Json(name = "location") val location: String?,
    @com.squareup.moshi.Json(name = "price") val price: Double = 0.0,
    @com.squareup.moshi.Json(name = "date") val date: String,
    @com.squareup.moshi.Json(name = "time") val time: String,
    @com.squareup.moshi.Json(name = "image_url") val imageUrl: String?,
    @com.squareup.moshi.Json(name = "organizer_id") val organizerId: String? = null,
    @com.squareup.moshi.Json(name = "max_capacity") val maxCapacity: Int? = 100,
    @com.squareup.moshi.Json(name = "created_at") val createdAt: String? = null,
    @com.squareup.moshi.Json(name = "currency_code") val currencyCode: String? = "EGP"
)

@com.squareup.moshi.JsonClass(generateAdapter = true)
data class SupabaseUser(
    @com.squareup.moshi.Json(name = "id") val id: String,
    @com.squareup.moshi.Json(name = "name") val name: String
)

@com.squareup.moshi.JsonClass(generateAdapter = true)
data class SupabaseAttendee(
    @com.squareup.moshi.Json(name = "event_id") val eventId: String,
    @com.squareup.moshi.Json(name = "user_id") val userId: String,
    @com.squareup.moshi.Json(name = "joined_at") val joinedAt: String? = null
)

@com.squareup.moshi.JsonClass(generateAdapter = true)
data class SupabaseEventTag(
    @com.squareup.moshi.Json(name = "event_id") val eventId: String,
    @com.squareup.moshi.Json(name = "tag_id") val tagId: Int
)

interface SupabaseApi {
    @GET("tags?select=*")
    suspend fun getTags(
        @Header("apikey") apiKey: String,
        @Header("Authorization") authorization: String
    ): List<SupabaseTag>

    @GET("events?select=*")
    suspend fun getEvents(
        @Header("apikey") apiKey: String,
        @Header("Authorization") authorization: String
    ): List<SupabaseEventResponse>

    @POST("events")
    suspend fun createEvent(
        @Header("apikey") apiKey: String,
        @Header("Authorization") authorization: String,
        @Header("Prefer") prefer: String = "return=representation",
        @Body event: SupabaseEvent
    ): List<SupabaseEventResponse>

    @GET("users?select=id,name")
    suspend fun getUsers(
        @Header("apikey") apiKey: String,
        @Header("Authorization") authorization: String
    ): List<SupabaseUser>

    @GET("attendees?select=*")
    suspend fun getAttendees(
        @Header("apikey") apiKey: String,
        @Header("Authorization") authorization: String
    ): List<SupabaseAttendee>

    @GET("event_tags?select=*")
    suspend fun getEventTags(
        @Header("apikey") apiKey: String,
        @Header("Authorization") authorization: String
    ): List<SupabaseEventTag>
}

object SupabaseClient {
    private const val BASE_URL = "https://icuvaldfjqmyirzmcjst.supabase.co/rest/v1/"
    const val API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImljdXZhbGRmanFteWlyem1janN0Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzAxNzA1NDMsImV4cCI6MjA4NTc0NjU0M30.9jYkDkLN1ro3esiQb1nzUUSbEEPMuX5MfSDxz6fkFqE"

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val api: SupabaseApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(httpClient)
        .build()
        .create(SupabaseApi::class.java)
}
