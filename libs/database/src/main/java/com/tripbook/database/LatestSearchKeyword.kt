package com.tripbook.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Clock

@Entity
data class LatestSearchKeyword(
    @PrimaryKey
    @ColumnInfo("keyword")
    val searchKeyword: String,

    @ColumnInfo("searchedAt")
    val searchedAt: Long = Clock.System.now().toEpochMilliseconds()
)