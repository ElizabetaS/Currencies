package com.example.elisp.valuti.klasi;

import java.io.Serializable;

/**
 * Created by elisp on 22.1.2018.
 */

public class Valuti implements Serializable {
    String id;
    String name;
    String rank;
    double price_usd;
    double price_btc;
    double percentage_change_1h;
    public double price_eur;
    public double available_supply;
    public Valuti()
    {

    }

    public Valuti(String id, String name, String rank, double price_usd, double price_btc, double percentage_change_1h) {
        this.id = id;
        this.name = name;
        this.rank = rank;
        this.price_usd = price_usd;
        this.price_btc = price_btc;
        this.percentage_change_1h = percentage_change_1h;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public double getPrice_usd() {
        return price_usd;
    }

    public void setPrice_usd(double price_usd) {
        this.price_usd = price_usd;
    }

    public double getPrice_btc() {
        return price_btc;
    }

    public void setPrice_btc(double price_btc) {
        this.price_btc = price_btc;
    }

    public double getPercentage_change_1h() {
        return percentage_change_1h;
    }

    public void setPercentage_change_1h(double percentage_change_1h) {
        this.percentage_change_1h = percentage_change_1h;
    }
}
