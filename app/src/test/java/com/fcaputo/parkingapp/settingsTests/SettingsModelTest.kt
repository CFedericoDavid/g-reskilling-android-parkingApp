package com.fcaputo.parkingapp.settingsTests

import com.fcaputo.parkingapp.mvp.model.SettingsModel
import com.fcaputo.parkingapp.utils.validation.ValidationErrorType
import org.junit.Assert
import org.junit.Test

class SettingsModelTest {
    private var model = SettingsModel()

    @Test
    fun `when ZERO is given to validate, model returns a validation result with SIZE_ZERO error`() {
        val result = model.validate(ZERO_INT)
        Assert.assertEquals(false, result.isSuccess)
        Assert.assertNotNull(result.error)
        Assert.assertEquals(ValidationErrorType.SIZE_IS_ZERO, result.error)
    }

    @Test
    fun `when a non-zero value is given to validate, model returns a success validation result`() {
        val result = model.validate(VALID_SIZE_INT)
        Assert.assertEquals(true, result.isSuccess)
        Assert.assertNull(result.error)
    }

    @Test
    fun `when a new size is given, model updates the setting`() {
        model.save(VALID_SIZE_INT)
        Assert.assertEquals(VALID_SIZE_INT, model.getParkingSize())
        model.save(ANOTHER_VALID_SIZE_INT)
        Assert.assertEquals(ANOTHER_VALID_SIZE_INT, model.getParkingSize())

    }

    companion object {
        private const val ZERO_INT = 0
        private const val VALID_SIZE_INT = 50
        private const val ANOTHER_VALID_SIZE_INT = 25
    }
}
