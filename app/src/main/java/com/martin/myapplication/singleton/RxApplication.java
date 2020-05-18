package com.martin.myapplication.singleton;

import android.content.Context;

import androidx.collection.LruCache;

import com.coppel.framework.core.singleton.BaseRxApplication;
import com.martin.myapplication.service.HeroNetworkService;

import java.lang.ref.WeakReference;

import io.reactivex.Single;
import io.realm.Realm;

public class RxApplication extends BaseRxApplication {

    private WeakReference<Context> context;
    private LruCache<String, Single<?>> requestCache = new LruCache(10);
    private static RxApplication rxApplication = new RxApplication();
    private HeroNetworkService heroNetworkService;

    public static RxApplication getInstance() {
        return rxApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = new WeakReference<>(this);
        Realm.init(this);
        rxApplication = this;
        heroNetworkService = new HeroNetworkService();
    }

    public Context getContext() {
        return context.get();
    }

    public String getResourceString(int stringId) {
        return getContext().getString(stringId);
    }

    public LruCache<String, Single<?>> getRequestCache() {
        return requestCache;
    }

    public HeroNetworkService getHeroNetworkService() {
        return heroNetworkService;
    }
}