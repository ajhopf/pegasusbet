package exceptions

class ResourceAlreadyExistsException extends RuntimeException {
    ResourceAlreadyExistsException(String message) {
        super(message)
    }
}
