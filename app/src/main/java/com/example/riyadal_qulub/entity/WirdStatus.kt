package com.example.riyadal_qulub.entity

sealed class WirdStatus {
    data object Done : WirdStatus()

    data object NotDone : WirdStatus()

    data object IsToday : WirdStatus()
}