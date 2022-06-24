package com.seziko.BinanceApi.service;

import com.binance.api.client.domain.market.TickerPrice;
import com.seziko.BinanceApi.entities.Binance;
import com.seziko.BinanceApi.results.DataResult;
import com.seziko.BinanceApi.results.Result;
import org.json.JSONArray;

import java.io.IOException;
import java.util.List;


public interface BinanceService {

    DataResult<List<Binance>> getAll();

    Result add(Binance binance);

    DataResult<List<Binance>> getFindBySembol(String sembol);

    Object getServiceData(String symbol) throws IOException;

    Object getAllServiceData() throws IOException;

    DataResult<Binance> getFindById(Long id);

    List<TickerPrice> allPriceBinanceApi();

    Result findBySymbolBinance(String symbol) throws IOException;


}