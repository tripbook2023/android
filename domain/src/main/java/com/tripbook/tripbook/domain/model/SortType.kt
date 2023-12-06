package com.tripbook.tripbook.domain.model

@Suppress("UNUSED")
enum class SortType(val typeName: String) {
    CREATED_ASC("최신순"), CREATED_DESC("오래된순"), POPULARITY("인기순");

    companion object {
        fun from(typeName: String) = SortType.entries.find { it.typeName == typeName } ?: CREATED_ASC
    }
}