package com.fcaputo.parkingapp.utils.validation

data class ValidationResult(val isSuccess: Boolean, val error: ValidationErrorType? = null)
