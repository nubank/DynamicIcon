package com.oguzhanaslann.dynamicicon

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.oguzhanaslann.dynamicicon.ui.theme.DynamicIconTheme

class UVSplashScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("HUE", "SplashScreenActivity.onCreate")

        setContent {
            DynamicIconTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Text(
                        text = "UVSplash Legacy",
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center,
                    )
                    Screen(
                        on30Click = {
                            Log.d("HUE", "30 Click")
                            scheduleChangeLauncherActivity(
                                activitiesEnabled = arrayOf(SplashScreenActivity::class.java.name),
                                activitiesDisabled = arrayOf("com.oguzhanaslann.dynamicicon.UVSplashScreenActivity")
                            )
                        },
                        on60Click = {
                            Log.d("HUE", "60 Click")
                            scheduleChangeLauncherActivity(
                                activitiesEnabled = arrayOf(ValhallaUVSplashScreenActivity::class.java.name),
                                activitiesDisabled = arrayOf("com.oguzhanaslann.dynamicicon.UVSplashScreenActivity")
                            )
                        }
                    )
                }
            }
        }
    }
}