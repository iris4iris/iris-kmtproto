package iris.kmtproto.api.user

import iris.kmtproto.client.ClientSession
import iris.kmtproto.client.SessionPasswordNeeded
import iris.kmtproto.client.SignUpRequired
import iris.kmtproto.client.TelegramClient
import iris.kmtproto.crypto.PasswordSrp
import iris.kmtproto.mtproto.RpcException
import iris.kmtproto.tl.gen.AccountGetPassword
import iris.kmtproto.tl.gen.AuthAuthorization
import iris.kmtproto.tl.gen.AuthAuthorizationCtor
import iris.kmtproto.tl.gen.AuthAuthorizationSignUpRequired
import iris.kmtproto.tl.gen.AuthCheckPassword
import iris.kmtproto.tl.gen.AuthImportBotAuthorization
import iris.kmtproto.tl.gen.AuthResendCode
import iris.kmtproto.tl.gen.AuthSendCode
import iris.kmtproto.tl.gen.AuthSentCode
import iris.kmtproto.tl.gen.AuthSentCodeCtor
import iris.kmtproto.tl.gen.AuthSentCodePaymentRequired
import iris.kmtproto.tl.gen.AuthSentCodeSuccess
import iris.kmtproto.tl.gen.AuthSignIn
import iris.kmtproto.tl.gen.CodeSettings
import iris.kmtproto.tl.gen.User
import iris.kmtproto.tl.gen.UserCtor
import kotlinx.coroutines.Deferred

class Auth(private val client: TelegramClient) {
    /**
     * Binds this auth_key to the bot. Skip if [TelegramClient.session] was already bound —
     * otherwise Telegram answers FLOOD_WAIT on importBotAuthorization.
     */
    fun importBotAuthorization(token: String): Deferred<User> =
        client.apiAsync { importBotAuthorizationSuspend(token) }

    internal suspend fun importBotAuthorizationSuspend(token: String): User {
        val saved = client.loadedSession
        if (saved != null && saved.userId != 0L) {
            try {
                client.getStateSuspend()
                client.user = restoreUser(saved)
                client.rememberUser(client.user!!)
                return client.user!!
            } catch (e: RpcException) {
                if (!isDeadAuth(e.message)) throw e
            }
        }
        val auth = client.invokeSuspend(
            AuthImportBotAuthorization(
                flags = 0,
                apiId = client.apiId,
                apiHash = client.apiHash,
                botAuthToken = token,
            ),
        )
        return applyAuth(auth)
    }

    /**
     * SMS / app-code. PHONE_MIGRATE_* is handled by [TelegramClient.invoke].
     * [AuthSentCodeSuccess] means this auth_key is already a user session.
     */
    fun sendCode(phone: String, settings: CodeSettings = CodeSettings()): Deferred<AuthSentCode> =
        client.apiAsync { sendCodeSuspend(phone, settings) }

    private suspend fun sendCodeSuspend(phone: String, settings: CodeSettings): AuthSentCode {
        val sent = client.invokeSuspend(
            AuthSendCode(
                phoneNumber = phone,
                apiId = client.apiId,
                apiHash = client.apiHash,
                settings = settings,
            ),
        )
        when (sent) {
            is AuthSentCodeSuccess -> applyAuth(sent.authorization)
            is AuthSentCodePaymentRequired ->
                error("auth.sentCodePaymentRequired — paid / premium auth, not implemented")
            is AuthSentCodeCtor -> Unit
            else -> error("unexpected auth.sentCode $sent")
        }
        return sent
    }

    fun resendCode(phone: String, phoneCodeHash: String, reason: String? = null): Deferred<AuthSentCode> =
        client.apiAsync {
            client.invokeSuspend(AuthResendCode(phoneNumber = phone, phoneCodeHash = phoneCodeHash, reason = reason))
        }

    /**
     * Completes [sendCode]. Throws [SessionPasswordNeeded] if 2FA is on — then [checkPassword].
     * Throws [SignUpRequired] if the number is not registered.
     */
    fun signIn(phone: String, phoneCodeHash: String, phoneCode: String): Deferred<User> =
        client.apiAsync { signInSuspend(phone, phoneCodeHash, phoneCode) }

    private suspend fun signInSuspend(phone: String, phoneCodeHash: String, phoneCode: String): User {
        val auth = try {
            client.invokeSuspend(
                AuthSignIn(
                    phoneNumber = phone,
                    phoneCodeHash = phoneCodeHash,
                    phoneCode = phoneCode,
                ),
            )
        } catch (e: RpcException) {
            if (e.message.contains("SESSION_PASSWORD_NEEDED")) {
                val hint = runCatching { client.invokeSuspend(AccountGetPassword).hint }.getOrNull()
                throw SessionPasswordNeeded(hint)
            }
            throw e
        }
        return applyAuth(auth, phone)
    }

    /** Cloud password (2FA) after [SessionPasswordNeeded]. */
    fun checkPassword(password: String): Deferred<User> = client.apiAsync { checkPasswordSuspend(password) }

    private suspend fun checkPasswordSuspend(password: String): User {
        val acc = client.invokeSuspend(AccountGetPassword)
        val srp = PasswordSrp.check(acc, password)
        return applyAuth(client.invokeSuspend(AuthCheckPassword(password = srp)))
    }

    private fun applyAuth(auth: AuthAuthorization, phone: String = ""): User {
        when (auth) {
            is AuthAuthorizationCtor -> {
                client.user = auth.user
                client.rememberUser(auth.user)
                client.loadedSession = client.session()
                return auth.user
            }
            is AuthAuthorizationSignUpRequired -> throw SignUpRequired(phone)
            else -> error("unexpected auth.authorization $auth")
        }
    }
}

private fun isDeadAuth(message: String): Boolean =
    message.contains("AUTH_KEY_UNREGISTERED") ||
        message.contains("SESSION_REVOKED") ||
        message.contains("AUTH_KEY_PERM_EMPTY") ||
        message.contains("USER_DEACTIVATED")

private fun restoreUser(session: ClientSession): UserCtor = UserCtor(
    id = session.userId,
    accessHash = session.accessHash.takeIf { it != 0L },
    bot = true,
    self = true,
)
