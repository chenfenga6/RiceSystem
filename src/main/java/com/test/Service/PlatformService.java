package com.test.Service;

import com.test.Entity.Platform;
import java.util.List;

public interface PlatformService {
    //新增平台
    String platform_add(Platform platform);

    //删除平台
    String platform_delete(Integer pid);

    //修改平台
    String platform_change(Platform platform);

    //查找平台信息
    Platform platform_find(Integer pid);

    //查看所有平台信息
    List<Platform> platform_findAll();
}
