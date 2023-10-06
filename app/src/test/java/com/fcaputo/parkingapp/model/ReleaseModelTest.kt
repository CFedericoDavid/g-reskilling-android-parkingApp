package com.fcaputo.parkingapp.model

import com.fcaputo.parkingapp.entity.Reservation
import com.fcaputo.parkingapp.mvp.contract.ReleaseContract
import com.fcaputo.parkingapp.mvp.model.ParkingData
import com.fcaputo.parkingapp.mvp.model.ParkingSettings
import com.fcaputo.parkingapp.mvp.model.ReleaseModel
import com.fcaputo.parkingapp.utils.Constants.ZERO_INT
import com.fcaputo.parkingapp.utils.getUtcCalendarInstance
import com.fcaputo.parkingapp.utils.plusHours
import com.fcaputo.parkingapp.utils.validation.ValidationErrorType.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.Calendar

class ReleaseModelTest {
    private lateinit var model: ReleaseContract.Model

    @Before
    fun setup() {
        ParkingSettings.size = VALID_SIZE
        ParkingData.reservations.clear()
        model = ReleaseModel()
    }

    @Test
    fun `when validating, if parkingNumber is zero, model returns corresponding error`() {
        val result = model.validateData(ZERO_INT, SECURITY_CODE)
        Assert.assertFalse(result.isSuccess)
        Assert.assertEquals(SPOT_IS_ZERO, result.error)
    }

    @Test
    fun `when validating, if parkingNumber is out of range, model returns corresponding error`() {
        val result = model.validateData(OUT_OF_RANGE_SPOT, SECURITY_CODE)
        Assert.assertFalse(result.isSuccess)
        Assert.assertEquals(SPOT_IS_OUT_OF_RANGE, result.error)
    }

    @Test
    fun `when validating, if both spot and code are incorrect, model returns corresponding error`() {
        ParkingData.reservations.add(Reservation(
            startDate = futureEarlierDate,
            endDate = futureLaterDate,
            parkingLot = FIRST_SPOT,
            securityCode = SECURITY_CODE
        ))
        ParkingData.reservations.add(Reservation(
            startDate = futureEarlierDate,
            endDate = futureLaterDate,
            parkingLot = SECOND_SPOT,
            securityCode = SECOND_SECURITY_CODE
        ))

        val result = model.validateData(THIRD_SPOT, THIRD_SECURITY_CODE)
        Assert.assertFalse(result.isSuccess)
        Assert.assertEquals(RESERVATION_NOT_FOUND, result.error)
    }

    @Test
    fun `when validating, if code is incorrect, model returns corresponding error`() {
        ParkingData.reservations.add(Reservation(
            startDate = futureEarlierDate,
            endDate = futureLaterDate,
            parkingLot = FIRST_SPOT,
            securityCode = SECURITY_CODE
        ))
        ParkingData.reservations.add(Reservation(
            startDate = futureEarlierDate,
            endDate = futureLaterDate,
            parkingLot = SECOND_SPOT,
            securityCode = SECOND_SECURITY_CODE
        ))

        val result = model.validateData(SECOND_SPOT, THIRD_SECURITY_CODE)
        Assert.assertFalse(result.isSuccess)
        Assert.assertEquals(RESERVATION_NOT_FOUND, result.error)
    }

    @Test
    fun `when validating, if both spot and code are correct, model returns success`() {
        ParkingData.reservations.add(Reservation(
            startDate = futureEarlierDate,
            endDate = futureLaterDate,
            parkingLot = FIRST_SPOT,
            securityCode = SECURITY_CODE
        ))
        ParkingData.reservations.add(Reservation(
            startDate = futureEarlierDate,
            endDate = futureLaterDate,
            parkingLot = SECOND_SPOT,
            securityCode = SECOND_SECURITY_CODE
        ))

        val result = model.validateData(SECOND_SPOT, SECOND_SECURITY_CODE)
        Assert.assertTrue(result.isSuccess)
        Assert.assertNull(result.error)
    }

    @Test
    fun `when given a correct spot and code combination, model releases the spot successfully`() {
        ParkingData.reservations.add(
            Reservation(
                startDate = futureEarlierDate,
                endDate = futureLaterDate,
                parkingLot = FIRST_SPOT,
                securityCode = SECURITY_CODE
            )
        )
        ParkingData.reservations.add(
            Reservation(
                startDate = futureEarlierDate,
                endDate = futureLaterDate,
                parkingLot = SECOND_SPOT,
                securityCode = SECOND_SECURITY_CODE
            )
        )

        Assert.assertEquals(TWO_INT, ParkingData.reservations.size)
        model.deleteReservation(SECOND_SPOT, SECOND_SECURITY_CODE)
        Assert.assertEquals(ONE_INT, ParkingData.reservations.size)
        Assert.assertFalse(ParkingData.reservations.any {
            it.parkingLot == SECOND_SPOT && it.securityCode == SECOND_SECURITY_CODE
        })
    }

        companion object {
        const val SECURITY_CODE = 1234
        const val SECOND_SECURITY_CODE = 9876
        const val THIRD_SECURITY_CODE = 5555
        const val VALID_SIZE = 100
        const val OUT_OF_RANGE_SPOT = 999
        const val FIRST_SPOT = 25
        const val SECOND_SPOT = 31
        const val THIRD_SPOT = 48

        const val ONE_INT = 1
        const val TWO_INT = 2

        val futureEarlierDate: Calendar = getUtcCalendarInstance().plusHours(ONE_INT)
        val futureLaterDate: Calendar = futureEarlierDate.plusHours(ONE_INT)
    }
}
