package iris.kmtproto.client

class SessionPasswordNeeded(val hint: String? = null) :
    RuntimeException(hint?.let { "SESSION_PASSWORD_NEEDED hint=$it" } ?: "SESSION_PASSWORD_NEEDED")

class SignUpRequired(val phone: String) :
    RuntimeException("auth.authorizationSignUpRequired for $phone")
