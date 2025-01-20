package com.teevee.apps.manager;

@JavaDerive(toString = true)
@Backing(type="int")
enum TellyAppsManagerInternalInstallFlags {
    UNINSTALL_DATA = 1 << 0,
    UNINSTALL_SYSTEM = 1 << 1
}
