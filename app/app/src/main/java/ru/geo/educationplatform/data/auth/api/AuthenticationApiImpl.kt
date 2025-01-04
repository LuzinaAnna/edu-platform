package ru.geo.educationplatform.data.auth.api

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import ru.geo.educationplatform.domain.auth.api.AuthenticationApi
import ru.geo.educationplatform.domain.auth.exception.InvalidCredentialsException
import ru.geo.educationplatform.domain.auth.exception.ObtainedBrokenTokensException
import ru.geo.educationplatform.domain.auth.repository.container.ApiTokens
import ru.geo.educationplatform.domain.auth.repository.container.Credentials
import javax.inject.Inject

class AuthenticationApiImpl @Inject constructor(
    private val client: OkHttpClient
): AuthenticationApi {

    companion object {
        const val LOGIN_URL = "http://192.168.0.25:8080/api/auth/login"
        val JSON_MEDIA_TYPE = "application/json; charset=utf-8".toMediaType()
    }

    override suspend fun authenticate(credentials: Credentials): ApiTokens {
        val gson = Gson()
        val serializedCredentials = gson.toJson(credentials)

        val authReq = Request.Builder()
            .url(LOGIN_URL)
            .post(serializedCredentials.toRequestBody(JSON_MEDIA_TYPE))
            .build()
        client.newCall(authReq).execute().use { response ->
            if (response.code == 401 || response.body == null) {
                throw InvalidCredentialsException()
            }
            val responseBody = response.body?.string()
            val tokens: ApiTokens?
            try {
                tokens = gson.fromJson(responseBody, ApiTokens::class.java)
            } catch (ex: JsonSyntaxException) {
                throw ObtainedBrokenTokensException()
            }
            return tokens!!
        }
    }
}