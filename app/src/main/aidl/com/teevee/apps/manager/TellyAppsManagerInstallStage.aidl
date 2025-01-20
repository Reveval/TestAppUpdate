package com.teevee.apps.manager;

@JavaDerive(toString = true)
@Backing(type="int")
enum TellyAppsManagerInstallStage {
    INVALID,
    PRE_INSTALL,
    DURING_INSTALL,
    POST_INSTALL,
    PRE_UNINSTALL,
    DURING_UNINSTALL,
    POST_UNINSTALL
}
