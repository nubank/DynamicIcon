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


///


class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        handleLauncherUpdate()
    }

    /// USECASE 4
    private fun handleLauncherUpdate() {
        Log.i("HUE", "handleLauncherUpdate")

        if (packageManager.getComponentEnabledSetting(
                ComponentName(packageName, ValhallaUVSplashScreenActivity::class.java.name)
            ) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED
        ) {
            Log.d("HUE", "Ja é ValhallaUVSplashScreenActivity")
            return
        }


        if (packageManager.getComponentEnabledSetting(
                ComponentName(packageName, ValhallaSplashScreenActivity::class.java.name)
            ) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED
        ) {
            Log.i("HUE", "ja é ValhallaSplashScreenActivity")
            return
        }

        if (packageManager.getComponentEnabledSetting(
                ComponentName(packageName, UVSplashScreenActivity::class.java.name)
            ) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED
        ) {
            Log.i("HUE", "vai ativar ValhallaUVSplashScreenActivity")
            scheduleChangeLauncherActivity(
                activitiesEnabled = arrayOf(ValhallaUVSplashScreenActivity::class.java.name),
                activitiesDisabled = arrayOf(
                    ValhallaSplashScreenActivity::class.java.name,
                    UVSplashScreenActivity::class.java.name,
                    SplashScreenActivity::class.java.name,
                ),
            )
        } else {
            if (packageManager.getComponentEnabledSetting(
                    ComponentName(packageName, SplashScreenActivity::class.java.name)
                ) == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT
            ) {
                Log.i("HUE", "vai ativar ValhallaSplashScreenActivity")
                scheduleChangeLauncherActivity(
                    activitiesEnabled = arrayOf(ValhallaSplashScreenActivity::class.java.name),
                    activitiesDisabled = arrayOf(
                        ValhallaUVSplashScreenActivity::class.java.name,
                        UVSplashScreenActivity::class.java.name,
                        SplashScreenActivity::class.java.name,
                    ),
                )
            }
        }
    }

    private fun isActivityEnabled(activityName: String): Boolean {
        return packageManager.getComponentEnabledSetting(
            ComponentName(packageName, activityName)
        ) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED || packageManager.getComponentEnabledSetting(
            ComponentName(packageName, activityName)
        ) == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT
    }
}
