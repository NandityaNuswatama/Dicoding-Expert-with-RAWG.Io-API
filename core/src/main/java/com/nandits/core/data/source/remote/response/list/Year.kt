package com.nandits.core.data.source.remote.response.list

data class Year(
    var count: Int?,
    var decade: Int?,
    var filter: String?,
    var from: Int?,
    var nofollow: Boolean?,
    var to: Int?,
    var years: List<YearX>?
)