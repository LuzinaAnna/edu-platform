package ru.geo.educationplatform.data.greeting.api

import okhttp3.OkHttpClient
import okhttp3.Request
import ru.geo.educationplatform.domain.auth.repository.container.ApiTokens
import ru.geo.educationplatform.domain.greeting.api.GreetingApi
import javax.inject.Inject

class GreetingApiImpl @Inject constructor(
    private val client: OkHttpClient
): GreetingApi {
    companion object {
        const val GREETING_URL = "http://192.168.0.25:8080/test"
    }

    override suspend fun greeting(tokens: ApiTokens): String {
        val greetingReq = Request.Builder()
            .url(GREETING_URL)
            .get()
            .header("Authorization","Bearer " + tokens.accessToken!!)
            .build()
        client.newCall(greetingReq).execute().use { response ->
                return response.body!!.string()
        }
    }
}