package com.martin.myapplication.Hero;

import androidx.annotation.Nullable;

import com.coppel.framework.core.model.BaseRepository;
import com.coppel.framework.core.util.Outcome;
import com.coppel.framework.core.util.OutcomePublishMapperKt;
import com.martin.myapplication.entities.Hero;
import com.martin.myapplication.service.HeroRemoteData;
import com.martin.myapplication.singleton.RxApplication;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.subjects.PublishSubject;

public class HeroRepository extends BaseRepository<List<Hero>> {

    private PublishSubject<Outcome<List<Hero>>> listadoOutcome;

    public HeroRepository() {
        super(RxApplication.getInstance().getRequestCache());
        initInternetConnection(RxApplication.getInstance().getContext());
    }

    @Nullable
    @Override
    public PublishSubject<Outcome<List<Hero>>> getOutcome() {
        if (listadoOutcome == null) {
            listadoOutcome = PublishSubject.create();
        }
        return listadoOutcome;
    }

    @NotNull
    @Override
    protected String getCacheKey() {
        return "ListadoSimpleRepository";
    }

    void fetch() {
        OutcomePublishMapperKt.loading(listadoOutcome, true);
        performRequest(getCachedObservable());
    }

    void refresh() {
        OutcomePublishMapperKt.loading(listadoOutcome, false);
        performRequest(buildFreshObservable());
    }

    @NotNull
    @Override
    protected Single<List<Hero>> buildFreshObservable() {
        HeroRemoteData heroRemoteData = new HeroRemoteData();

        Single<List<Hero>> observable = heroRemoteData
                .getHero()
                .cache()
                .map(s -> {
                    List<Hero> hero = s.getData().getHero();
                    return hero;
                });

        responseCache.put(getCacheKey(), observable);
        return observable;
    }
}
