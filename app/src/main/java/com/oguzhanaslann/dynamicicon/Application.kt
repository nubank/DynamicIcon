package com.oguzhanaslann.dynamicicon

import android.app.Application
import android.content.ComponentName
import android.content.pm.PackageManager
import android.util.Log

/*
 * Created by Thiago Zagui Giacomini on 19/12/2024.
 * See thiagozg on GitHub: https://github.com/thiagozg
 */

/// Goal:
// 1. Incluir uma ValhallaActivity no Manifest
// 2. Caso o icone seja 60: manter ele sendo exibido - sem criar 2 icones
// 3. Caso o icone seja 30: manter ele possibilitando a alteração para o icone 60


/// Issue 1 - problema dos 2 icones/2 splashs
// 1. Legacy30 -> Valhalla30
//      a. Legacy -> Valhalla
// 2. Legacy60 -> Valhalla60
//      a. UV -> UVValhalla
// 3. Legacy30 -> Valhalla60
//      a. Legacy -> UVValhalla

/// Issue 2 - Precisar startar uma activity que foi desabilitada anteriormente


class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        // TODO: ignorar ativar a Splash Legacy

        handleLauncherUpdate()
    }

    private fun handleLauncherUpdate() {
        Log.i("HUE", "handleLauncherUpdate")
        val icon60Legacy =
            packageManager.getComponentEnabledSetting(ComponentName(packageName, "UVSplashScreenActivity"))
        if (icon60Legacy == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
            Log.i("HUE", "vai ativar ValhallaUVSplashScreenActivity")
            scheduleChangeLauncherActivity(
                activitiesEnabled = arrayOf(
                    ValhallaUVSplashScreenActivity::class.java.name,
                    SplashScreenActivity::class.java.name
                ),
                activitiesDisabled = arrayOf(
                    ValhallaSplashScreenActivity::class.java.name,
                    "UVSplashScreenActivity",
                ),
            )
        } else {
            if (packageManager.getComponentEnabledSetting(
                    ComponentName(
                        packageName,
                        ValhallaUVSplashScreenActivity::class.java.name
                    )
                ) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED
            ) {
                return
            }
            val mainSplashActivity =
                packageManager.getComponentEnabledSetting(
                    ComponentName(packageName, SplashScreenActivity::class.java.name)
                )
            if (mainSplashActivity == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
                Log.i("HUE", "vai ativar ValhallaActivityIcon30")
                scheduleChangeLauncherActivity(
                    activitiesEnabled = arrayOf(ValhallaSplashScreenActivity::class.java.name),
                    activitiesDisabled = arrayOf(
                        ValhallaUVSplashScreenActivity::class.java.name,
                        "UVSplashScreenActivity",
                        SplashScreenActivity::class.java.name,
                    ),
                )
            }
        }
    }
}
