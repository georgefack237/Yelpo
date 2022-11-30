package com.health13.yelpo.data.models

import com.google.gson.annotations.SerializedName
data class Category(
    val alias: String,
    val title: String,
    @SerializedName("parent_aliases") val parentAliases: List<String>
    )

data class CategoryResponse(
    val categories: List<Category>
)