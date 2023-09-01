package com.fcaputo.parkingapp.entity

import java.util.Calendar

data class Reservation(val startDate: Calendar, val endDate: Calendar, val parkingLot: Int, val securityCode: Int)
