package com.nearor.framwork.network;

/**
 *
 * Created by Nearor on 16/6/6.
 */
public abstract class APIService<T> {

    private ICallManager manager;
    private Class<T> mApiInterface;
    private T api;

    private APIService() {}

    protected APIService(ICallManager manager,Class<T> ApiInterface) {
        this.manager = manager;
        mApiInterface = ApiInterface;
    }

    public <R> void startCall(APICall<R> call, APICallBack<R> callBack){
        call.enqueue(manager,callBack);//异步
    }

    protected T getApi(){
        if(api == null){
            api = RetrofitHelper.getSharedInstance().create(mApiInterface);
        }
        return api;
    }

    protected ICallManager getManager() {
        return manager;
    }
}
