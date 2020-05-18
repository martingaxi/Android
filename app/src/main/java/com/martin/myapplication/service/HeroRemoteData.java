package com.martin.myapplication.service;

import com.coppel.framework.core.model.entities.StructureResponse;
import com.martin.myapplication.entities.DataHero;
import com.martin.myapplication.singleton.RxApplication;

import io.reactivex.Single;

public class HeroRemoteData {
    public Single<StructureResponse<DataHero>> getHero() {
        HeroApiInterface heroApiInterface = RxApplication.getInstance()
                .getHeroNetworkService()
                .getRetrofitApi();

        return heroApiInterface.getHero();
    }
}