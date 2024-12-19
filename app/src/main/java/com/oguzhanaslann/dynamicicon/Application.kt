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
class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        // TODO: ignorar ativar a Splash Legacy

        handleLauncherUpdate()
    }

    private fun handleLauncherUpdate() {
        Log.i("HUE", "handleLauncherUpdate")
        val icon60Legacy =
            packageManager.getComponentEnabledSetting(ComponentName(packageName, mainActivityAlias))
        if (icon60Legacy == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
            Log.i("HUE", "vai ativar ValhallaActivityIcon60")
            scheduleChangeLauncherActivity(
                activitiesEnabled = arrayOf(ValhallaActivityIcon60::class.java.name),
                activitiesDisabled = arrayOf(
                    ValhallaActivityIcon30::class.java.name,
                    mainActivityAlias,
                    mainActivity,
                ),
            )
        } else {
            Log.i("HUE", "vai ativar ValhallaActivityIcon30")
            scheduleChangeLauncherActivity(
                activitiesEnabled = arrayOf(ValhallaActivityIcon30::class.java.name),
                activitiesDisabled = arrayOf(
                    ValhallaActivityIcon60::class.java.name,
                    mainActivityAlias,
                    mainActivity,
                ),
            )
        }
    }
}
