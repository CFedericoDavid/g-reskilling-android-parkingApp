package com.fcaputo.parkingapp.entity

import java.time.LocalDateTime

data class Reservation(val startDate: LocalDateTime, val endDate: LocalDateTime, val parkingLot: Int, val securityCode: Int)
