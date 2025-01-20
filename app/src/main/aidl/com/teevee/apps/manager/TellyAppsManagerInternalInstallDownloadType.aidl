package com.teevee.apps.manager;

@JavaDerive(toString = true)
@Backing(type="int")
enum TellyAppsManagerInternalInstallDownloadType {
    LOCAL_FILE,
    HTTP,
    DOWNLOAD_MANAGER,
    FETCH
}
