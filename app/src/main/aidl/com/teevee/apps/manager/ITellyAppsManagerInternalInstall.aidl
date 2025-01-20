package com.teevee.apps.manager;

import android.net.Uri;
import com.teevee.apps.manager.TellyAppsManagerInternalInstallDownloadType;
import com.teevee.apps.manager.ITellyAppsManagerInternalInstallCallback;
import com.teevee.apps.manager.TellyAppsManagerInternalInstallFlags;

interface ITellyAppsManagerInternalInstall {
    const String PACKAGE_NAME = "com.teevee.apps.manager";
    const String SERVICE_NAME = "com.teevee.apps.manager.InternalInstallService";

    oneway void install(in Uri uri, in TellyAppsManagerInternalInstallDownloadType downloadType, in ITellyAppsManagerInternalInstallCallback callback);
    oneway void uninstall(in List<String> packageNames, in ITellyAppsManagerInternalInstallCallback callback);
    oneway void uninstallAll(in TellyAppsManagerInternalInstallFlags flags, in ITellyAppsManagerInternalInstallCallback callback);
    oneway void abort();
}
