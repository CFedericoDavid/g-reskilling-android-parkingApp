package com.fcaputo.parkingapp.model

import com.fcaputo.parkingapp.entity.Reservation
import com.fcaputo.parkingapp.mvp.contract.ReservationContract
import com.fcaputo.parkingapp.mvp.model.ParkingData
import com.fcaputo.parkingapp.mvp.model.ParkingSettings
import com.fcaputo.parkingapp.mvp.model.ReservationModel
import com.fcaputo.parkingapp.utils.Constants.ZERO_INT
import com.fcaputo.parkingapp.utils.getUtcCalendarInstance
import com.fcaputo.parkingapp.utils.minusHours
import com.fcaputo.parkingapp.utils.minusMinutes
import com.fcaputo.parkingapp.utils.plusHours
import com.fcaputo.parkingapp.utils.plusMinutes
import com.fcaputo.parkingapp.utils.validation.ValidationErrorType
import com.fcaputo.parkingapp.utils.validation.ValidationResult
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.Calendar

class ReservationModelTest {
    private val model: ReservationContract.Model = ReservationModel()

    @Before
    fun setup() {
        ParkingSettings.size = PARKING_SIZE
        ParkingData.reservations.clear()
    }

    @Test
    fun `when a reservation is given, model stores it correctly`() {
        val reservation = Reservation(
            startDate = futureEarlierDate,
            endDate = futureLaterDate,
            parkingLot = VALID_SPOT,
            securityCode = SECURITY_CODE
        )

        Assert.assertEquals(ZERO_INT, model.getReservations().size)
        model.storeReservation(reservation)
        Assert.assertEquals(ONE, model.getReservations().size)
    }

    /* VALIDATION TESTS */
    @Test
    fun `when no size was set, the corresponding error is returned`() {
        ParkingSettings.size = ZERO_INT

        val result = model.validateData(
            startDate = futureEarlierDate,
            endDate = futureLaterDate,
            parkingSpot = VALID_SPOT
        )

        Assert.assertEquals(ValidationResult(isSuccess = false, ValidationErrorType.SIZE_NOT_SET), result)
    }

    @Test
    fun `when start date is in the past, the corresponding error is returned`() {
        val result = model.validateData(
            startDate = pastDate,
            endDate = futureLaterDate,
            parkingSpot = VALID_SPOT
        )

        Assert.assertEquals(ValidationResult(isSuccess = false, ValidationErrorType.DATETIMES_ARE_PAST), result)
    }

    @Test
    fun `when end date is in the past, the corresponding error is returned`() {
        val result = model.validateData(
            startDate = futureEarlierDate,
            endDate = pastDate,
            parkingSpot = VALID_SPOT
        )

        Assert.assertEquals(ValidationResult(isSuccess = false, ValidationErrorType.DATETIMES_ARE_PAST), result)
    }

    @Test
    fun `when start & end date are unordered, the corresponding error is returned`() {
        val result = model.validateData(
            startDate = futureLaterDate,
            endDate = futureEarlierDate,
            parkingSpot = VALID_SPOT
        )

        Assert.assertEquals(ValidationResult(isSuccess = false, ValidationErrorType.DATETIMES_ARE_UNORDERED), result)
    }

    @Test
    fun `when spot is zero, the corresponding error is returned`() {
        val result = model.validateData(
            startDate = futureEarlierDate,
            endDate = futureLaterDate,
            parkingSpot = ZERO_INT
        )

        Assert.assertEquals(ValidationResult(isSuccess = false, ValidationErrorType.SPOT_IS_ZERO), result)
    }

    @Test
    fun `when spot is greater than the size, the corresponding error is returned`() {
        val result = model.validateData(
            startDate = futureEarlierDate,
            endDate = futureLaterDate,
            parkingSpot = OUT_OF_RANGE_SPOT
        )

        Assert.assertEquals(ValidationResult(isSuccess = false, ValidationErrorType.SPOT_IS_OUT_OF_RANGE), result)
    }

    @Test
    fun `when there is a reservation for that spot between the submitted dates, the corresponding error is returned`() {
        val submittedStartDate = futureEarlierDate
        val submittedEndDate = futureLaterDate

        //both reservation dates fall in between the submitted dates
        // ---S---R---R'---S'---  S: submitted; R: reservation
        model.storeReservation(Reservation(
            startDate = submittedStartDate.plusMinutes(DIFF_IN_MINUTES),
            endDate = submittedEndDate.minusMinutes(DIFF_IN_MINUTES),
            parkingLot = VALID_SPOT,
            securityCode = SECURITY_CODE
        ))

        val result = model.validateData(
            startDate = submittedStartDate,
            endDate = submittedEndDate,
            parkingSpot = VALID_SPOT
        )

        Assert.assertEquals(ValidationResult(isSuccess = false, ValidationErrorType.SPOT_IS_UNAVAILABLE), result)
    }

    @Test
    fun `when there is a reservation for that spot where the start date overlaps, the corresponding error is returned`() {
        val submittedStartDate = futureEarlierDate
        val submittedEndDate = futureLaterDate

        //the submitted dates overlap with the reservation start date
        // ---S---R---S'---R'---  S: submitted; R: reservation
        model.storeReservation(Reservation(
            startDate = submittedStartDate.plusMinutes(DIFF_IN_MINUTES),
            endDate = submittedEndDate.plusMinutes(DIFF_IN_MINUTES),
            parkingLot = VALID_SPOT,
            securityCode = SECURITY_CODE
        ))

        val result = model.validateData(
            startDate = submittedStartDate,
            endDate = submittedEndDate,
            parkingSpot = VALID_SPOT
        )

        Assert.assertEquals(ValidationResult(isSuccess = false, ValidationErrorType.SPOT_IS_UNAVAILABLE), result)
    }

    @Test
    fun `when there is a reservation for that spot where the end date overlaps, the corresponding error is returned`() {
        val submittedStartDate = futureEarlierDate
        val submittedEndDate = futureLaterDate

        //the submitted dates overlap with the reservation end date
        // ---R---S---R'---S'---  S: submitted; R: reservation
        model.storeReservation(Reservation(
            startDate = submittedStartDate.minusMinutes(DIFF_IN_MINUTES),
            endDate = submittedEndDate.minusMinutes(DIFF_IN_MINUTES),
            parkingLot = VALID_SPOT,
            securityCode = SECURITY_CODE
        ))

        val result = model.validateData(
            startDate = submittedStartDate,
            endDate = submittedEndDate,
            parkingSpot = VALID_SPOT
        )

        Assert.assertEquals(ValidationResult(isSuccess = false, ValidationErrorType.SPOT_IS_UNAVAILABLE), result)
    }

    @Test
    fun `when there is a reservation for that spot where both submitted dates are between its dates, the corresponding error is returned`() {
        val submittedStartDate = futureEarlierDate
        val submittedEndDate = futureLaterDate

        //the submitted dates fall in between the reservation dates
        // ---R---S---S'---R'---  S: submitted; R: reservation
        model.storeReservation(Reservation(
            startDate = submittedStartDate.minusMinutes(DIFF_IN_MINUTES),
            endDate = submittedEndDate.plusMinutes(DIFF_IN_MINUTES),
            parkingLot = VALID_SPOT,
            securityCode = SECURITY_CODE
        ))

        val result = model.validateData(
            startDate = submittedStartDate,
            endDate = submittedEndDate,
            parkingSpot = VALID_SPOT
        )

        Assert.assertEquals(ValidationResult(isSuccess = false, ValidationErrorType.SPOT_IS_UNAVAILABLE), result)
    }

    @Test
    fun `when all the data is valid, and there is no overlapping reservations for that spot, a success result is returned`() {
        val submittedStartDate = futureEarlierDate
        val submittedEndDate = futureLaterDate

        //safe: both reservation dates are past the submitted end date
        // ---S---S'---R---R'---  S: submitted; R: reservation
        model.storeReservation(Reservation(
            startDate = submittedEndDate.plusHours(DIFF_IN_HOURS),
            endDate = submittedEndDate.plusHours(DIFF_IN_HOURS).plusMinutes(DIFF_IN_MINUTES),
            parkingLot = VALID_SPOT,
            securityCode = SECURITY_CODE
        ))
        //safe: both reservation dates are before the submitted start date
        // ---R---R'---S---S'---  S: submitted; R: reservation
        model.storeReservation(Reservation(
            startDate = submittedStartDate.minusHours(DIFF_IN_HOURS).minusMinutes(DIFF_IN_MINUTES),
            endDate = submittedStartDate.minusHours(DIFF_IN_HOURS),
            parkingLot = VALID_SPOT,
            securityCode = SECURITY_CODE
        ))

        val result = model.validateData(
            startDate = submittedStartDate,
            endDate = submittedEndDate,
            parkingSpot = VALID_SPOT
        )

        Assert.assertEquals(ValidationResult(isSuccess = true), result)
    }

    @Test
    fun `when there is no reservations for that spot, a success result is returned`() {
        val submittedStartDate = futureEarlierDate
        val submittedEndDate = futureLaterDate

        val result = model.validateData(
            startDate = submittedStartDate,
            endDate = submittedEndDate,
            parkingSpot = VALID_SPOT
        )

        Assert.assertEquals(ValidationResult(isSuccess = true), result)
    }

    @Test
    fun `automatic release system successfully releases all past reservations`() {
        model.storeReservation(
            Reservation(
                startDate = firstPastStartDate,
                endDate = firstPastEndDate,
                parkingLot = VALID_SPOT,
                securityCode = SECURITY_CODE
            )
        )
        model.storeReservation(
            Reservation(
                startDate = secondPastStartDate,
                endDate = secondPastEndDate,
                parkingLot = VALID_SPOT,
                securityCode = SECURITY_CODE
            )
        )
        model.storeReservation(
            Reservation(
                startDate = futureEarlierDate,
                endDate = futureLaterDate,
                parkingLot = VALID_SPOT,
                securityCode = SECURITY_CODE
            )
        )

        Assert.assertEquals(THREE, model.getReservations().size)
        model.releasePastReservations()
        Assert.assertEquals(ONE, model.getReservations().size)
        Assert.assertFalse(model.getReservations().any { it.endDate.before(getUtcCalendarInstance()) })
    }

    companion object {
        const val PARKING_SIZE = 100
        const val VALID_SPOT = 10
        const val OUT_OF_RANGE_SPOT = 999
        const val SECURITY_CODE = 1234

        const val ONE = 1
        const val TWO = 2
        const val THREE = 3
        const val DIFF_IN_HOURS = ONE
        const val DIFF_IN_MINUTES = 15

        //REFERENCE DATE TIMES
        val pastDate: Calendar = getUtcCalendarInstance().minusHours(ONE)
        val futureEarlierDate: Calendar = getUtcCalendarInstance().plusHours(ONE)
        val futureLaterDate: Calendar = futureEarlierDate.plusHours(ONE)

        //RELEASE SYSTEM DATES
        val firstPastStartDate: Calendar = getUtcCalendarInstance()
            .minusHours(ONE)
            .minusMinutes(DIFF_IN_MINUTES)
        val firstPastEndDate: Calendar = getUtcCalendarInstance().minusHours(ONE)
        val secondPastStartDate: Calendar = getUtcCalendarInstance()
            .minusHours(TWO)
            .minusMinutes(DIFF_IN_MINUTES)
        val secondPastEndDate: Calendar = getUtcCalendarInstance().minusHours(TWO)
    }
}
