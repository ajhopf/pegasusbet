package exceptions

class InsuficientFundsException extends RuntimeException{
    InsuficientFundsException(String message) {
        super(message)
    }
}
