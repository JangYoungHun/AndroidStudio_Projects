package com.example.volley_practice;

public class MovieData {


    String name;
    String rank;
    String show_Cnt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getShow_Cnt() {
        return show_Cnt;
    }

    public void setShow_Cnt(String show_Cnt) {
        this.show_Cnt = show_Cnt;
    }

    MovieData(String name, String rank, String show_Cnt){
        this.name = name;
        this.rank = rank;
        this.show_Cnt = show_Cnt;
    }


}
