package com.seziko.BinanceApi.bussiness;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.TickerPrice;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.seziko.BinanceApi.binanceDao.BinanceDao;
import com.seziko.BinanceApi.entities.Binance;
import com.seziko.BinanceApi.results.DataResult;
import com.seziko.BinanceApi.results.Result;
import com.seziko.BinanceApi.results.SuccessDataResult;
import com.seziko.BinanceApi.results.SuccessResult;
import com.seziko.BinanceApi.service.BinanceService;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

@Service
public class BinanceManagement implements BinanceService {

    private BinanceDao binanceDao;

    public BinanceManagement(BinanceDao binanceDao) {
        this.binanceDao = binanceDao;
    }

    @Override
    public DataResult<List<Binance>> getAll() {
        return new SuccessDataResult<List<Binance>>(
                this.binanceDao.findAll(),
                "Listelendi..");
    }

    @Override
    public DataResult<Binance> getFindById(Long id) {
        return new SuccessDataResult<Binance>(
                this.binanceDao.findById(id),
                "Listelendi..");
    }

    @Override
    public Result add(Binance binance) {
        this.binanceDao.save(binance);
        return new SuccessResult("Veri Eklendi..");
    }

    @Override
    public DataResult<List<Binance>> getFindBySembol(String sembol) {
        return new SuccessDataResult<List<Binance>>(
                this.binanceDao.getFindBySembol(sembol), "listelendi..");
    }


    public Object getServiceData(String symbol) throws IOException {

        Binance binance = new Binance();
        URL url = new URL("https://api.binance.com/api/v3/ticker/price?symbol="+symbol);
        URLConnection yc = url.openConnection();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        yc.getInputStream()
                )
        );
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            return inputLine;
        return inputLine;
    }


    public Object getAllServiceData() throws IOException {

        URL url = new URL("https://api.binance.com/api/v3/ticker/price?");
        URLConnection yc = url.openConnection();
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = new JsonObject();

        Binance binance = new Binance();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        yc.getInputStream()
                )
        );
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            // System.out.println(jsonObject.get("name").getAsString()); //John

            return inputLine;

        return inputLine;
    }
    @Override
    public List<TickerPrice> allPriceBinanceApi(){
        Binance binance = new Binance();
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance("API-KEY", "SECRET");
        BinanceApiRestClient client = factory.newRestClient();
        List<TickerPrice> allPrices = client.getAllPrices();


        return allPrices;
    }
    @Override
    public Result findBySymbolBinance(String symbol){
        Binance binance = new Binance();
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance("API-KEY", "SECRET");
        BinanceApiRestClient client = factory.newRestClient();

        TickerPrice prices = client.getPrice(symbol);

        String sembol;
        String fiyat;

        binance.sembol = prices.getSymbol();
        binance.fiyat = prices.getPrice();

        binanceDao.save(binance);
        return new SuccessResult("Başarıyla eklendi..");

    }
}
