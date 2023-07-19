package net.sevendays.android.code_check.api

import com.google.gson.annotations.SerializedName
import net.sevendays.android.code_check.item.RepositoryItem

data class RepositorySearchResponse(
    @SerializedName("items")
    val repositories: List<RepositoryItem>
)