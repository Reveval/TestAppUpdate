package com.teevee.apps.manager;

import com.teevee.apps.manager.TellyAppsManagerProgressStage;

@JavaDerive(toString = true)
parcelable TellyAppsManagerProgressResult {
    TellyAppsManagerProgressStage stage;
    int progress;
    String message;
}