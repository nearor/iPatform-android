package com.nearor.common.route;


import java.util.List;

/**
 *
 * Created by Nearor on 6/1/16..
 */
public interface IModuleMap {
    List<Module> getModules();
    Module getModule(String name);
}
