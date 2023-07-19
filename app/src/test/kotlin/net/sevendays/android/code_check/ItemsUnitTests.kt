package net.sevendays.android.code_check

import net.sevendays.android.code_check.api.GitHubApiService
import net.sevendays.android.code_check.api.RepositorySearchResponse
import net.sevendays.android.code_check.item.Owner
import net.sevendays.android.code_check.item.RepositoryItem
import okhttp3.ResponseBody
import org.junit.Test
import org.junit.Assert.*
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Call
import retrofit2.Response

class ItemsUnitTests {

    @Test
    fun testFetchItems() {
        val mockService: GitHubApiService = mock()
        val query = "Kotlin"
        val header = "application/vnd.github.text-match+json"
        val testRepo = RepositoryItem(
            0,
            "Kotlin",
            "",
            Owner(""),
            "",
            0,
            0,
            0,
            0
        )
        val successResponse: Response<RepositorySearchResponse> = Response.success(
            RepositorySearchResponse(listOf(testRepo))
        )
        val call: Call<RepositorySearchResponse> = mock()

        whenever(mockService.searchRepositories(header, query)).thenReturn(call)
        whenever(call.execute()).thenReturn(successResponse)

        val response: Response<RepositorySearchResponse> =
            mockService.searchRepositories(header, query).execute()

        assertEquals(true, response.isSuccessful)
        response.body()?.repositories?.let {
            assertEquals(listOf(testRepo), it)
        }
    }

    @Test
    fun testErrorState() {
        val mockService: GitHubApiService = mock()
        val query = ""
        val header = "application/vnd.github.text-match+json"
        val errorResponse: Response<RepositorySearchResponse> = Response.error(
             404,
            ResponseBody.create(null, "Error")
        )
        val call: Call<RepositorySearchResponse> = mock()

        whenever(mockService.searchRepositories(header, query)).thenReturn(call)
        whenever(call.execute()).thenReturn(errorResponse)

        val response: Response<RepositorySearchResponse> =
            mockService.searchRepositories(header, query).execute()

        assertEquals(false, response.isSuccessful)
    }

}