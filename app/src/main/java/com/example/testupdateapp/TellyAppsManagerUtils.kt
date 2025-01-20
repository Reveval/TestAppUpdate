package com.example.testupdateapp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.IBinder
import androidx.core.content.ContextCompat
import com.teevee.apps.manager.ITellyAppsManagerInternalInstall
import com.teevee.apps.manager.ITellyAppsManagerInternalInstallCallback
import com.teevee.apps.manager.TellyAppsManagerInstallResult
import com.teevee.apps.manager.TellyAppsManagerProgressResult
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object TellyAppsManagerUtils {
    private fun wrapCallback(
        onSuccess: (List<TellyAppsManagerInstallResult>) -> Unit,
        onProgress: (stage: Int, percentage: Int, message: String) -> Unit,
        onFailure: () -> Unit,
        close: () -> Unit
    ) = object : ITellyAppsManagerInternalInstallCallback.Stub() {
        override fun onSuccess(
            results: MutableList<TellyAppsManagerInstallResult>
        ) {
            onSuccess(results)
            close()
        }

        override fun onProgressChange(
            result: TellyAppsManagerProgressResult
        ) {
            onProgress(result.stage, result.progress, result.message)
        }

        override fun onFailure() {
            onFailure()
            close()
        }
    }

    fun startInternalUpdate(
        context: Context,
        uri: Uri,
        updateDownloadManager: UpdateDownloadManager,
        onSuccess: () -> Unit,
        onProgress: (stage: Int, percentage: Int, message: String) -> Unit,
        onFailure: () -> Unit
    ) {
        context.bindService(
            ComponentName(
                ITellyAppsManagerInternalInstall.PACKAGE_NAME,
                ITellyAppsManagerInternalInstall.SERVICE_NAME
            ),
            onConnect = { (binder, close) ->
                val service = ITellyAppsManagerInternalInstall.Stub.asInterface(binder)
                service.install(
                    uri,
                    updateDownloadManager.value,
                    wrapCallback(
                        onSuccess = { results ->
                            onSuccess()
                        },
                        onProgress = onProgress,
                        onFailure = onFailure,
                        close = close
                    )
                )
            },
            onDisconnect = {
                onFailure()
            }
        )
    }

    suspend fun abortInternalUpdate(
        context: Context
    ) = suspendCoroutine { cont ->
        context.bindService(
            ComponentName(
                ITellyAppsManagerInternalInstall.PACKAGE_NAME,
                ITellyAppsManagerInternalInstall.SERVICE_NAME
            ),
            onConnect = { (binder, close) ->
                val service = ITellyAppsManagerInternalInstall.Stub.asInterface(binder)
                service.abort()
                close()
                cont.resume(Unit)
            },
            onDisconnect = {
                cont.resume(Unit)
            }
        )
    }

    fun startFactoryInstall(
        context: Context,
        path: String
    ) {
        val intent = Intent(FACTORY_ACTION_LOCAL_INSTALL)
            .setComponent(FACTORY_COMPONENT)
            .putExtra(FACTORY_EXTRA_LOCAL_INSTALL_FILE, path)
        ContextCompat.startForegroundService(context, intent)
    }

    private val FACTORY_COMPONENT = ComponentName.createRelative(
        "com.teevee.apps.manager",
        ".FactoryService"
    )
    private const val FACTORY_ACTION_LOCAL_INSTALL =
        "com.teevee.apps.manager.action.LOCAL_INSTALL"
    private const val FACTORY_EXTRA_LOCAL_INSTALL_FILE =
        "com.teevee.apps.manager.extra.LOCAL_INSTALL_FILE"
}

internal fun Context.bindService(
    componentName: ComponentName,
    onConnect: (Pair<IBinder, () -> Unit>) -> Unit,
    onDisconnect: () -> Unit = {}
) {
    val intent = Intent().apply {
        component = componentName
        setPackage(componentName.packageName)
    }

    val conn = object : ServiceConnection {
        override fun onServiceConnected(
            name: ComponentName,
            service: IBinder
        ) {
            onConnect(service to { unbindService(this) })
        }

        override fun onServiceDisconnected(
            name: ComponentName
        ) {
            onDisconnect()
        }
    }

    bindService(
        intent,
        conn,
        Context.BIND_AUTO_CREATE
    )
}
