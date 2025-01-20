package com.teevee.apps.manager;

import com.teevee.apps.manager.TellyAppsManagerInstallStage;
import com.teevee.apps.manager.TellyAppsManagerInstallStatus;

@JavaDerive(toString = true)
parcelable TellyAppsManagerInstallResult {
    String packageName;
    TellyAppsManagerInstallStage stage;
    TellyAppsManagerInstallStatus status;
    String message;
}