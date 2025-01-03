package pegasusdatastore

import grails.converters.JSON

class AuthInterceptor {
    AuthInterceptor() {
        matchAll()
    }

    boolean before() {
        String bearer = request.getHeader('Authorization')

        if (!bearer || !bearer.startsWith("Bearer ")) {
            log.warn "Authorization header is missing or invalid"
            response.status = 401
            render([message: "Unauthorized: Missing or invalid token"] as JSON)
            return false
        }

        String token = bearer.substring(7)
        String baseUrl = "http://localhost:8083/api/validate"

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(baseUrl).openConnection()
            connection.setRequestMethod("GET")
            connection.setRequestProperty("Authorization", "Bearer ${token}")
            connection.connect()

            int responseCode = connection.responseCode

            if (responseCode == 200) {
                return true
            }
        } catch (Exception e) {
            log.error "Error validating token: ${e.message}", e
        }

        response.status = 401
        render([message: "Unauthorized: Invalid or expired token"] as JSON)
        return false
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
