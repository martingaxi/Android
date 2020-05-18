package com.martin.myapplication.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataHero {
    @SerializedName("hero")
    @Expose
    private List<Hero> hero;
    public List<Hero> getHero() {return hero;}
    public void setHero(List<Hero> hero) {this.hero = hero;}


}


