package com.example.apps.manager;

import com.example.apps.manager.TellyAppsManagerProgressStage;

@JavaDerive(toString = true)
parcelable TellyAppsManagerProgressResult {
    TellyAppsManagerProgressStage stage;
    int progress;
    String message;
}