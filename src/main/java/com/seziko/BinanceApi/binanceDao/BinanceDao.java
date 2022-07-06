package com.seziko.BinanceApi.binanceDao;

import com.seziko.BinanceApi.entities.Binance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BinanceDao extends JpaRepository<Binance,Integer> {



    List<Binance> getFindBySembol(String sembol);


    Binance findById(Long id);

    @Query("Select sembol,fiyat From Binance Where sembol = :symbolName and fiyat=:price")
    List<Binance> getBySymbolAndPrice(String symbolName,String price);


}
