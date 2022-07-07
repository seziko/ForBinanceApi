package com.seziko.BinanceApi.service;

import com.binance.api.client.domain.market.TickerPrice;
import com.seziko.BinanceApi.entities.Binance;
import com.seziko.BinanceApi.results.DataResult;
import com.seziko.BinanceApi.results.Result;
import com.seziko.BinanceApi.results.SuccessDataResult;

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

    SuccessDataResult<List<Binance>> connectBinanceWebSocket(String symbol);

    DataResult<List<Binance>> getAllSorted();

    DataResult<List<Binance>> getAll(int pageNo, int pageSize);

    Result findByCount();

    Result closeWs(String symbol) throws IOException;

    List<Binance> getBySymbolAndPrice(String symbolName, String price);

}