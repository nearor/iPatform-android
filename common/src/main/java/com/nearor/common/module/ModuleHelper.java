package com.nearor.common.module;

import android.app.Application;

import com.nearor.common.route.Route;

import java.util.List;

/**
 * Created by Nearor on 6/2/16.
 */
public class ModuleHelper {

    public static void roleAppinit(Application application){
        Route.getSharedInstance().init(application);
        List<Module> modules = RoleAppModuleMap.getInstance().getModules();
        for (Module module:modules) {
            Route.getSharedInstance().registerActivityModule(module.getName(),module.getPath());
        }
    }
}
