package ru.geo.educationplatform.domain.auth

import android.util.Log
import ru.geo.educationplatform.domain.auth.api.AuthenticationApi
import ru.geo.educationplatform.domain.auth.exception.InvalidCredentialsException
import ru.geo.educationplatform.domain.auth.exception.ObtainedBrokenTokensException
import ru.geo.educationplatform.domain.auth.repository.JwtStoreRepository
import ru.geo.educationplatform.domain.auth.repository.container.AuthenticationStatus
import ru.geo.educationplatform.domain.auth.repository.container.Credentials
import java.io.IOException
import javax.inject.Inject

class AuthenticationManager @Inject constructor(
    private val jwtStoreRepository: JwtStoreRepository,
    private val authenticationApi: AuthenticationApi
) {

    suspend fun auth(credentials: Credentials): AuthenticationStatus {
        try {
            val tokens = authenticationApi.authenticate(credentials)
            jwtStoreRepository.store(tokens)
            return AuthenticationStatus.SUCCESSFUL
        } catch (ex: IOException) {
            Log.e("auth", ex.toString())
            return AuthenticationStatus.SERVER_ERROR
        } catch (ex: ObtainedBrokenTokensException ) {
            return AuthenticationStatus.SERVER_ERROR
        } catch (ex: InvalidCredentialsException) {
            return AuthenticationStatus.INVALID_CREDENTIALS
        }
    }
}
