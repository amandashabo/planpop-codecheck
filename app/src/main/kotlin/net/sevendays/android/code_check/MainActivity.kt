/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package net.sevendays.android.code_check

import android.app.Application
import android.graphics.Color
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import net.sevendays.android.code_check.ui.ItemsSearchList

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            window.navigationBarColor = Color.TRANSPARENT
            ItemsSearchList()
        }
    }

}

@HiltAndroidApp
class MyApp: Application()