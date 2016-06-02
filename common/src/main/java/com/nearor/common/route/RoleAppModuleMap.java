package com.nearor.common.route;


import com.nearor.framwork.util.Lg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nearor on 6/1/16.
 */
public class RoleAppModuleMap implements IModuleMap{

    private static final String TAG = Lg.makeLogTag(RoleAppModuleMap.class);

    public static final String MODULE_NAME_LOGIN = "login";

    private List<Module> mModules;
    private Map<String,Module> mModuleMap = new HashMap<> ();

    private RoleAppModuleMap(){
        mModules = initModules();
    }

    private static class Holder{
       static RoleAppModuleMap sInstance = new RoleAppModuleMap();
    }

    public static RoleAppModuleMap getInstance(){
        return Holder.sInstance;
    }

    @Override
    public List<Module> getModules() {
        return mModules;
    }

    @Override
    public Module getModule(String name) {
        return mModuleMap.get(name);
    }

    private List<Module> initModules(){
        List<Module> modules = new ArrayList<>();
        for (RoleAppModule appModule : RoleAppModule.values()) {
            Module module = new Module(appModule.getModuleName(),appModule.getModuleType(),"");
            modules.add(module);
            mModuleMap.put(appModule.getModuleName(),module);
        }
        return modules;
    }

    private enum RoleAppModule{
        LOGIN(MODULE_NAME_LOGIN,"com.nearor.myroleapp.login.LoginActivity");

        private String moduleName;
        private String moduleType;

        RoleAppModule(String moduleName, String moduleType) {
            this.moduleName = moduleName;
            this.moduleType = moduleType;
        }

        public String getModuleName() {
            return moduleName;
        }

        public String getModuleType() {
            return moduleType;
        }
    }


}
