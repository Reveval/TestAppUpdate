package com.example.apps.manager;

import com.example.apps.manager.TellyAppsManagerInstallResult;
import com.example.apps.manager.TellyAppsManagerProgressResult;

oneway interface ITellyAppsManagerInternalInstallCallback {
    oneway void onSuccess(in List<TellyAppsManagerInstallResult> results);
    oneway void onProgressChange(in TellyAppsManagerProgressResult result);
    oneway void onFailure();
}
