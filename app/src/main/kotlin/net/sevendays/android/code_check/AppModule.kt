package net.sevendays.android.code_check

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.sevendays.android.code_check.api.GitHubApiService
import net.sevendays.android.code_check.api.RetrofitClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGitHubApiService(): GitHubApiService {
        return RetrofitClient.instance
    }

}