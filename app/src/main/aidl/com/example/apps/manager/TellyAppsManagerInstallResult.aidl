package com.example.apps.manager;

import com.example.apps.manager.TellyAppsManagerInstallStage;
import com.example.apps.manager.TellyAppsManagerInstallStatus;

@JavaDerive(toString = true)
parcelable TellyAppsManagerInstallResult {
    String packageName;
    TellyAppsManagerInstallStage stage;
    TellyAppsManagerInstallStatus status;
    String message;
}