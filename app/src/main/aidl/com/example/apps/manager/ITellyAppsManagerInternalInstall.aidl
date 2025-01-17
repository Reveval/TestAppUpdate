package com.example.apps.manager;

import android.net.Uri;
import com.example.apps.manager.TellyAppsManagerInternalInstallDownloadType;
import com.example.apps.manager.ITellyAppsManagerInternalInstallCallback;
import com.example.apps.manager.TellyAppsManagerInternalInstallFlags;

interface ITellyAppsManagerInternalInstall {
    const String PACKAGE_NAME = "com.example.apps.manager";
    const String SERVICE_NAME = "com.example.apps.manager.InternalInstallService";

    oneway void install(in Uri uri, in TellyAppsManagerInternalInstallDownloadType downloadType, in ITellyAppsManagerInternalInstallCallback callback);
    oneway void uninstall(in List<String> packageNames, in ITellyAppsManagerInternalInstallCallback callback);
    oneway void uninstallAll(in TellyAppsManagerInternalInstallFlags flags, in ITellyAppsManagerInternalInstallCallback callback);
    oneway void abort();
}
