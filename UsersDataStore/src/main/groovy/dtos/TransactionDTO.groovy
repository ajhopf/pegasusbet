package dtos

import enums.TransactionType

class TransactionDTO {
    BigDecimal amount
    TransactionType transactionType
}
