package com.seziko.BinanceApi.binanceDao;

import com.seziko.BinanceApi.entities.Binance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BinanceDao extends JpaRepository<Binance,Integer> {



    List<Binance> getFindBySembol(String sembol);


    Binance findById(Long id);
}
