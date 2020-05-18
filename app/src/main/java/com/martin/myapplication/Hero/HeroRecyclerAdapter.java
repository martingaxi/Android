package com.martin.myapplication.Hero;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.coppel.framework.core.view.list.ActionModeRecyclerViewHolder;
import com.martin.myapplication.R;
import com.martin.myapplication.entities.Hero;

import java.util.ArrayList;
import java.util.List;

public class HeroRecyclerAdapter extends ListAdapter<Hero, HeroRecyclerAdapter.CentroViewHolder> {
    public HeroRecyclerAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public CentroViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_hero, viewGroup, false);
        return new CentroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CentroViewHolder centroViewHolder, int i) {
        centroViewHolder.bindTo(getItem(i));
    }

    class CentroViewHolder extends ActionModeRecyclerViewHolder {

        TextView idTextView;
        TextView heroTextView;
        TextView fullNameTextView;
        TextView publisherTextView;
        LinearLayout linearLayout;

        CentroViewHolder(View view) {
            super(view);

            idTextView= view.findViewById(R.id.textView_nombre);
            heroTextView = view.findViewById(R.id.textView_nombre);
            fullNameTextView = view.findViewById(R.id.textView_telefono);
            publisherTextView = view.findViewById(R.id.textView_pais);
            linearLayout = view.findViewById(R.id.linearLayout_contacto);
        }

        void bindTo(Hero hero) {
            idTextView.setText(hero.getId() );
            heroTextView.setText(hero.getName() );
            fullNameTextView.setText(hero.getFullname());
            publisherTextView.setText(hero.getPublisher());

        }
    }

    private static final DiffUtil.ItemCallback<Hero> DIFF_CALLBACK = new DiffUtil.ItemCallback<Hero>() {
        @Override
        public boolean areItemsTheSame(@NonNull Hero hero, @NonNull Hero t1) {
            return hero.getId() == t1.getId();
        }

        @Override
        @SuppressLint("DiffUtilEquals")
        public boolean areContentsTheSame(@NonNull Hero hero, @NonNull Hero t1) {
            return hero.equals(t1);
        }
    };


    void submitList(List<Hero> hero, String searchQuery) {
        if (hero.isEmpty() || (searchQuery == null || searchQuery.isEmpty())) {
            submitList(hero);
        } else {
            List<Hero> filteredList = new ArrayList<>();
            String searchQueryLowerCase = searchQuery.toLowerCase();

            for (Hero hero1 : hero) {
                String isIdHero = hero1.getId().toString();
                Boolean id = isIdHero.toLowerCase().contains(searchQueryLowerCase);
                Boolean isNomEmpEqual = hero1.getName().toLowerCase().contains(searchQueryLowerCase);
                Boolean isTelEmpEqual = hero1.getFullname().toLowerCase().contains(searchQueryLowerCase);
                Boolean isPublisherHero = hero1.getPublisher().toLowerCase().contains(searchQueryLowerCase);


                if (isNomEmpEqual || isTelEmpEqual || id || isPublisherHero) {
                    filteredList.add(hero1);
                }
            }

            submitList(filteredList);
        }
    }
}