package com.oguzhanaslann.dynamicicon

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oguzhanaslann.dynamicicon.ui.theme.DynamicIconTheme


class SplashScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DynamicIconTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Screen(
                        on30Click = {
                            Log.d("HUE", "30 Click")
                            scheduleChangeLauncherActivity(
                                activitiesEnabled = arrayOf(SplashScreenActivity::class.java.name),
                                activitiesDisabled = arrayOf("UVSplashScreenActivity")
                            )
                        },
                        on60Click = {
                            Log.d("HUE", "60 Click")
                            scheduleChangeLauncherActivity(
                                activitiesEnabled = arrayOf("UVSplashScreenActivity"),
                                activitiesDisabled = arrayOf(SplashScreenActivity::class.java.name)
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Screen(
    on30Click: () -> Unit = {},
    on60Click: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Button(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp),
            onClick = on30Click
        ) {
            Text(text = "30")
        }

        Button(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp),
            onClick = on60Click
        ) {
            Text(text = "60")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun previewScreen() {
    Screen()
}
