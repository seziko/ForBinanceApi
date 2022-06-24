package com.seziko.BinanceApi.entities;

import com.binance.api.client.domain.market.TickerPrice;
import com.google.gson.JsonElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "binance")
@NoArgsConstructor
@AllArgsConstructor
public class Binance {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Column(name = "sembol")
    public String sembol;

    @Column(name = "fiyat")
    public String fiyat;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSembol(JsonElement symbol) {
        return sembol;
    }

    public void setSembol(String symbol) {
        this.sembol = sembol;
    }

    public String getFiyat(JsonElement price) {
        return fiyat;
    }

    public void setFiyat(String price) {
        this.fiyat = fiyat;
    }
}
