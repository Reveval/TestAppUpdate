package com.teevee.apps.manager;

import com.teevee.apps.manager.TellyAppsManagerInstallResult;
import com.teevee.apps.manager.TellyAppsManagerProgressResult;

oneway interface ITellyAppsManagerInternalInstallCallback {
    oneway void onSuccess(in List<TellyAppsManagerInstallResult> results);
    oneway void onProgressChange(in TellyAppsManagerProgressResult result);
    oneway void onFailure();
}
