package com.example.testupdateapp

import android.content.Context
import android.util.Log
import androidx.core.net.toUri
import com.teevee.apps.manager.TellyAppsManagerProgressStage
import kotlin.math.round

object Util {

    fun runAppsBundleInstall(
        context: Context,
        bundleName: String = "",
        url: String,
        updateDownloadManager: UpdateDownloadManager,
        bundleSize: Int = 1,
        bundleIndex: Int = 0,
    ) {
        val isUserdebug = false
        val name = if (isUserdebug) {
            bundleName
        } else {
            null
        }
        val downloadingText = if (name.isNullOrBlank()) {
            "Downloading Apps…"
        } else {
            "Downloading $name"
        }
        val downloadingMessageText = "We're downloading your Telly applications. Please do not turn off your device. Your device may automatically restart during the update process."
        val extractingText = "Extracting Telly Apps…"
        val installingTextDynamic = "Installing App…"
        val installingTextFixed = "Installing Telly Apps…"

        //uiProgressController.update(uiProgressToken, title = downloadingText)

        // current not used by Telly apps.bundle
        val ignoredStages = arrayOf(
            TellyAppsManagerProgressStage.INSTALLING_OBB,
            TellyAppsManagerProgressStage.EXTRACTING,
            TellyAppsManagerProgressStage.UNINSTALLING
        )

        val validStages = setOf(
            TellyAppsManagerProgressStage.DOWNLOADING,
            TellyAppsManagerProgressStage.INSTALLING_APK
        )

        val stagePercentage = 1F / validStages.size

        var userCanceled = false
        var handledInExtractStep = false

        var updateAction = AppBundleUpdateAction.DOWNLOAD

        /*if (bundleIndex == 0) {
            broadcastAppBundleUpdate(
                AnalyticsAppBundleUpdate(
                    bundleName = bundleName,
                    action = updateAction,
                    state = AppBundleUpdateState.START
                )
            )
        }*/

        TellyAppsManagerUtils.startInternalUpdate(
            context = context,
            uri = url.toUri(),
            updateDownloadManager = updateDownloadManager,
            onSuccess = {
                Log.e("ISSUE", "onSuccess")
            },
            onProgress = onProgress@{ stage, percentage, message ->
                Log.e("ISSUE", "onProgress, stage: $stage, percentage: $percentage, message: $message")
                if (stage in ignoredStages) {
                    // removed from list below
                    return@onProgress
                }

                if (stage == TellyAppsManagerProgressStage.INSTALLING_APK &&
                    updateAction == AppBundleUpdateAction.DOWNLOAD
                ) {
                    if (bundleIndex + 1 == bundleSize) {

                    }
                    updateAction = AppBundleUpdateAction.INSTALL
                    if (bundleIndex == 0) {

                    }
                }

                val relative = (stage * stagePercentage * 100) + (percentage * stagePercentage)

                val percentageBundleBlock = (100 / bundleSize)
                val progressBundle =
                    percentageBundleBlock * (bundleIndex) + relative.toInt().normalize(
                        0,
                        100,
                        0,
                        percentageBundleBlock
                    )

                val text = when (stage) {
                    TellyAppsManagerProgressStage.DOWNLOADING -> downloadingText
                    TellyAppsManagerProgressStage.INSTALLING_APK -> installingTextDynamic.format(message)

                    else -> ""
                }
                val messageText = when (stage) {
                    TellyAppsManagerProgressStage.DOWNLOADING -> downloadingMessageText
                    else -> ""
                }

                if (
                    stage == TellyAppsManagerProgressStage.EXTRACTING && !handledInExtractStep
                ) {

                    handledInExtractStep = true
                }


                /*sendState(
                    state = STATE_INSTALLING_APPS_BUNDLE,
                    progress = minOf(progressBundle, 100),
                    message = text,
                    showAds = false
                )*/
            },
            onFailure = {
                Log.e("ISSUE", "onFailure")
                /*broadcastAppBundleUpdate(
                    AnalyticsAppBundleUpdate(
                        bundleName = bundleName,
                        action = updateAction,
                        state = AppBundleUpdateState.ERROR
                    )
                )
                tamDownloadTimeout.cancel()
                if (userCanceled) {
                    uiProgressController.finish(uiProgressToken, 0)
                    sendState(STATE_NO_UPDATE)
                    cancel()
                } else {
                    retryAppsInstall(
                        bundleName,
                        url,
                        updateDownloadManager,
                        uiProgressToken,
                        bundleSize,
                        bundleIndex,
                        onComplete
                    )
                }*/
            }
        )
    }
}

enum class AppBundleUpdateAction(val value: String) {
    DOWNLOAD("download"),
    INSTALL("install")
}

fun Int.normalize(minOld: Int, maxOld: Int, minNew: Int, maxNew: Int): Int {
    val oldValue = if (this < minOld) minOld
    else if (this > maxOld) maxOld
    else this
    val oldRange = maxOld.toDouble() - minOld.toDouble()
    val newRange = maxNew.toDouble() - minNew.toDouble()
    val newValue =
        ((oldValue.toDouble() - minOld.toDouble()) * newRange / oldRange) + minNew.toDouble()
    return round(newValue).toInt()
}
