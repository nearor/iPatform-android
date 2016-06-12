package com.nearor.framwork.network;


import retrofit.Call;

/**
 * 实现 {@link Call} 和 Activity 或者 Fragment 生命周期的绑定
 * Created by nearor.
 */
public interface ICallManager {
    /**
     * 往 Manager 中添加需要被管理的 {@link Call}
     * @param call {@link Call}
     */
    void addCall(Call call);

    /**
     * 移除已添加的 call。{@link #addCall(Call)}
     * @param call {@link Call}
     */
    void removeCall(Call call);

    /**
     * Manager 销毁的时候调用，用来 cancel 掉还在执行的 call
     */
    void onManagerDestroy();
}
