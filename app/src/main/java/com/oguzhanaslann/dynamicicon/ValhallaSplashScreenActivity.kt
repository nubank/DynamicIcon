package com.oguzhanaslann.dynamicicon

import android.content.Intent
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

open class ValhallaSplashScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DynamicIconTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Text(
                        text = "Valhalla",
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center,
                    )
                    Screen(
                        on30Click = {
                            Log.d("HUE", "30 Click - INTENT")
                            startActivity(Intent(this, SplashScreenActivity::class.java))
                        },
                        on60Click = {
                            Log.d("HUE", "60 Click - NOTHING")
                        }
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }
}

