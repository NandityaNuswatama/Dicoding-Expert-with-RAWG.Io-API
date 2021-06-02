package com.nandits.core.data.source.remote.response.list

data class ListGameResponse(
    var count: Int?,
    var description: String?,
    var filters: Filters?,
    var next: String?,
    var nofollow: Boolean?,
    var nofollow_collections: List<String>,
    var noindex: Boolean?,
    var previous: Any?,
    var results: List<Result>,
    var seo_description: String?,
    var seo_h1: String?,
    var seo_keywords: String?,
    var seo_title: String?
)