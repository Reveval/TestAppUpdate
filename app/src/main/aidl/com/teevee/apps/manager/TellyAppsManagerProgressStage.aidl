package com.teevee.apps.manager;

@JavaDerive(toString = true)
@Backing(type="int")
enum TellyAppsManagerProgressStage {
    DOWNLOADING,
    EXTRACTING,
    INSTALLING_APK,
    INSTALLING_OBB,
    UNINSTALLING
}