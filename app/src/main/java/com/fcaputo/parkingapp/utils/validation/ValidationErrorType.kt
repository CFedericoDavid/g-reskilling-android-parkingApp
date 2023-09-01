package com.fcaputo.parkingapp.utils.validation

enum class ValidationErrorType {
    INCOMPLETE_FIELD,
    SIZE_IS_ZERO,
    DATETIMES_ARE_PAST,
    DATETIMES_ARE_UNORDERED,
    SPOT_IS_OUT_OF_RANGE,
    SPOT_IS_ZERO,
    SPOT_IS_UNAVAILABLE,
    SIZE_NOT_SET
}
