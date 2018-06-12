package com.han.base.other;

import java.util.List;

/**
 * @author hongchang
 * @description: 权限申请回调的接口
 */
public interface PermissionListener {

    void onGranted();

    void onDenied(List<String> deniedPermissions);
}
