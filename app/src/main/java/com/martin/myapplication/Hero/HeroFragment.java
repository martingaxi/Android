package com.martin.myapplication.Hero;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.coppel.framework.core.util.LiveDataExtensionsKt;
import com.coppel.framework.core.util.Outcome;
import com.coppel.framework.core.view.list.BaseListFragment;
import com.coppel.framework.core.view.list.LoadingView;
import com.coppel.framework.core.view.list.SearchViewHandler;
import com.coppel.framework.core.view.list.SimpleDividerItemDecoration;
import com.martin.myapplication.R;
import com.martin.myapplication.entities.Hero;
import com.martin.myapplication.singleton.RxApplication;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HeroFragment  extends BaseListFragment implements SearchViewHandler.SearchListener {
    static String tag = "HeroFragment";
    private HeroViewModel heroViewModel;
    private HeroRecyclerAdapter heroRecyclerAdapter = new HeroRecyclerAdapter();
    private Observer<Outcome<List<Hero>>> heroListObserver;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setToolbarTitle(RxApplication.getInstance().getResourceString(R.string.listado));

        View view = inflater.inflate(R.layout.fragment_hero, container, false);
        LoadingView loadingView = view.findViewById(R.id.loading_view);
        setLoadingView(loadingView);

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        setSwipeRefreshLayout(swipeRefreshLayout);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        setRecyclerView(recyclerView);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prepareSwipeRefreshLayout();

        if (getRecyclerView() != null) {
            getRecyclerView().setAdapter(heroRecyclerAdapter);
            getRecyclerView().setLayoutManager(new LinearLayoutManager(getContext()));
        }
        searchViewHandler = new SearchViewHandler(
                this,
                this,
                getSwipeRefreshLayout(),
                getHeroViewModel().getSearchQuery()
        );

        LiveDataExtensionsKt.reObserve(getHeroViewModel().getHeroOutcome(), this, getHeroListObserver());
        getHeroViewModel().fetch();
    }

    private void prepareSwipeRefreshLayout() {
        if (getSwipeRefreshLayout() != null) {
            getSwipeRefreshLayout().setOnRefreshListener(() -> getHeroViewModel().refresh());
        }
    }

    private HeroViewModel getHeroViewModel() {
        if (heroViewModel == null) {
            HeroViewModel.FactoryVM heroViewModelFactoryVM = new HeroViewModel.FactoryVM();
            heroViewModel = new ViewModelProvider(this, heroViewModelFactoryVM).get(HeroViewModel.class);
        }

        return heroViewModel;
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_search_only, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_option).getActionView();
        searchViewHandler.initialize(searchView);
    }

    private Observer<Outcome<List<Hero>>> getHeroListObserver() {
        if (heroListObserver == null && getRecyclerView() != null) {
            heroListObserver = (outcome -> validateOutcomeFromResult(outcome, getRecyclerView()));
        }

        return heroListObserver;
    }

    @Override
    public <T> void showListFromResult(@org.jetbrains.annotations.Nullable Outcome<List<T>> outcome) {
        if (outcome instanceof Outcome.Success) {
            Outcome.Success outcomeSuccess = (Outcome.Success) outcome;

            @SuppressWarnings("unchecked")
            List<Hero> hero = (List<Hero>) outcomeSuccess.getData();

            //heroRecyclerAdapter.submitList(hero);

            String searchQuery = getHeroViewModel().getSearchQuery().getValue();

            heroRecyclerAdapter.submitList(hero, searchQuery);

            if (getRecyclerView() != null && getContext() != null) {
                getRecyclerView().addItemDecoration(new SimpleDividerItemDecoration(getContext()));
            }
        }
    }
    @Override
    public void onSearchQuerySubmit(@NotNull String searchQuery) {
        showListFromResult(getHeroViewModel().getHeroOutcome().getValue());
    }

    @Override
    public void onSearchQueryCleared() {
        showListFromResult(getHeroViewModel().getHeroOutcome().getValue());
    }
}