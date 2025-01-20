package com.teevee.apps.manager;

@JavaDerive(toString = true)
@Backing(type="int")
enum TellyAppsManagerInstallStatus {
    INVALID,
    ABORTED,
    BLOCKED,
    CONFLICT,
    INCOMPATIBLE,
    STORAGE,
    TIMEOUT,
    UNKNOWN,
    SUCCESS,
    NONEXISTENT,
    HIGHER_OR_EQUALS_VERSION
}