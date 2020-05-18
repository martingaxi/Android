package com.martin.myapplication.Hero;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.coppel.framework.core.util.OutcomePublishMapperKt;
import com.martin.myapplication.entities.Hero;
import com.coppel.framework.core.util.Outcome;
import com.coppel.framework.core.view.list.BaseListViewModel;


import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HeroViewModel extends BaseListViewModel {
    private LiveData<Outcome<List<Hero>>> heroOutcome;
    private HeroRepository heroRepository;

    LiveData<Outcome<List<Hero>>> getHeroOutcome() {
        if (heroOutcome == null && getHeroRepository().getOutcome() != null) {
            heroOutcome = OutcomePublishMapperKt.toLiveData(getHeroRepository().getOutcome(), getCompositeDisposable());
        }

        return heroOutcome;
    }

    protected HeroRepository getHeroRepository() {
        if (heroRepository == null) {
            heroRepository = new HeroRepository();
        }

        return heroRepository;
    }

    void fetch() {
        if (getHeroOutcome() != null) {
            getHeroRepository().fetch();
        }
    }

    void refresh() {
        getHeroRepository().refresh();
    }

    public static class FactoryVM implements ViewModelProvider.Factory {

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new HeroViewModel();
        }
    }
}
