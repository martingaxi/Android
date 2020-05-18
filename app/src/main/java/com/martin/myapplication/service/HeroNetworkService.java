package com.martin.myapplication.service;

import com.coppel.framework.core.domain.NetworkService;
import com.martin.myapplication.BuildConfig;

import java.util.Collections;

public class HeroNetworkService extends NetworkService<HeroApiInterface> {

    public HeroNetworkService() {
        super(
                BuildConfig.urlApiHero,
                HeroApiInterface.class,
                Collections.emptyList(),
                300
        );
    }
}

