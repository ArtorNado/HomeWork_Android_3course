package com.example.homeworks.common.exception

sealed class DomainException(error: String): Exception(error)

object LettersNotAllowedException: DomainException("Value cannot be letter or sign")

object InsufficientDataException: DomainException("Insufficient data")

object UnexpectedException: DomainException("Unexpected error")

