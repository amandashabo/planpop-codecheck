package net.sevendays.android.code_check.item

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RepositoryItem(
    val id: Int,
    val name: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("owner")
    val ownerIconUrl: Owner,
    val language: String,
    @SerializedName("stargazers_count")
    val stargazersCount: Long,
    @SerializedName("watchers_count")
    val watchersCount: Long,
    @SerializedName("forks_count")
    val forksCount: Long,
    @SerializedName("open_issues_count")
    val openIssuesCount: Long
) : Parcelable

@Parcelize
data class Owner(
    @SerializedName("avatar_url")
    val avatar_url: String
): Parcelable