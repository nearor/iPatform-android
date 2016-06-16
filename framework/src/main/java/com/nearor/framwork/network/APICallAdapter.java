package com.nearor.framwork.network;

import android.support.annotation.Nullable;


import com.nearor.framwork.concurency.MainThreadExecutor;

import java.io.IOException;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * {@link retrofit2.Call} to {@link APICall}
 * Created by nearor on 1/31/16.
 */
class APICallAdapter<T> implements APICall<T> {

    private final Call<T> call;
    private Executor mCallBackExecutor = new MainThreadExecutor();

    public APICallAdapter(Call<T> call) {
        this.call = call;
    }

    @Override
    public void cancel() {
        call.cancel();
    }


    @Override
    public void enqueue(final ICallManager manager, final APICallBack<T> callback) {
        if (manager != null) {
            manager.addCall(call);
        }

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, final Response<T> response) {
                mCallBackExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        callFinished(manager);

                        if (callback != null) {
                            if (response.body() != null) {
                                callback.onSuccess(response.body());
                            } else {
                                callback.onError(new Throwable("Response.body is null"), null);
                            }
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<T> call, final Throwable t) {
                mCallBackExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        callFinished(manager);
                        if (callback != null) {
                            if (t instanceof APIResponseException) {
                                callback.onError(t, ((APIResponseException) t).getApiResponse());
                            } else {
                                callback.onError(t, null);
                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    public T execute() throws IOException {
        Response<T> response = call.execute();
        return response == null ? null : response.body();
    }

    @Override
    public APICall<T> clone() {
        return new APICallAdapter<>(call.clone());
    }

    private void callFinished(@Nullable ICallManager manager) {
        if (manager != null) {
            manager.removeCall(call);
        }
    }
}
