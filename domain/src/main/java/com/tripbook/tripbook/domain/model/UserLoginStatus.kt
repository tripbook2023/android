package com.tripbook.tripbook.domain.model

enum class UserLoginStatus{
    STATUS_NORMAL,
    STATUS_REQUIRED_AUTH;

    companion object {
        fun from(rawValue: String?) = rawValue?.let {
            valueOf(rawValue)
        } ?: STATUS_REQUIRED_AUTH
    }
}
