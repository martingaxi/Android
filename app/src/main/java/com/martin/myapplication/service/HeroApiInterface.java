package com.martin.myapplication.service;

import com.coppel.framework.core.model.entities.StructureResponse;
import com.martin.myapplication.entities.DataHero;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface HeroApiInterface {
    @GET("hero")
    Single<StructureResponse<DataHero>> getHero();
}