package com.seziko.BinanceApi.bussiness;

import com.binance.api.client.BinanceApiCallback;
import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.BinanceApiWebSocketClient;
import com.binance.api.client.domain.event.AggTradeEvent;
import com.binance.api.client.domain.market.CandlestickInterval;
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
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.xml.ws.WebServiceException;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

@Service
//@KafkaListener(topics = "javaguides",groupId = "myGroup")
public class BinanceManagement implements BinanceService {

    private BinanceDao binanceDao;
    @Autowired
    public BinanceManagement(BinanceDao binanceDao) {
        this.binanceDao = binanceDao;
    }

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    public BinanceManagement(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
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


    @Override
    public SuccessDataResult<List<Binance>> connectBinanceWebSocket(String symbol) {
        BinanceApiWebSocketClient client = BinanceApiClientFactory.newInstance().newWebSocketClient();
        Binance binance = new Binance();

        client.onAggTradeEvent(symbol, (AggTradeEvent response) -> {

            String sembol = response.getSymbol();
            String fiyat = response.getPrice();

            binance.sembol = sembol;
            binance.fiyat = fiyat;
            binance.id = response.getAggregatedTradeId();
            //binanceDao.save(binance);
            kafkaTemplate.send("javaguides",binance.toString());
            binanceDao.save(binance);


            System.out.println("id: "+binance.getId()+" sembol: "+binance.getSembol()+" fiyat :"+binance.getFiyat());
        });

        return new SuccessDataResult<List<Binance>>(this.binanceDao.findAll(),"kayıtlar listelenmiştir.");

    }

    @Override
    public DataResult<List<Binance>> getAllSorted() {
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        return new SuccessDataResult<List<Binance>>
                (this.binanceDao.findAll(sort),"Başarılı..");
    }

    @Override
    public DataResult<List<Binance>> getAll(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1,pageSize);
        return new SuccessDataResult<List<Binance>>(this.binanceDao.findAll(pageable).getContent(),pageNo+"/"+pageSize);
    }

    @Override
    public Result findByCount() {
        long count = binanceDao.count();
        return new SuccessResult("Toplam "+count+" adet listelenmiştir."
        );
    }

    @Override
    public Result closeWs(String symbol) throws IOException {
        return null;
    }

    @Override
    public List<Binance> getBySymbolAndPrice(String symbolName, String price) {
        return this.binanceDao.getBySymbolAndPrice(symbolName,price);
    }
}
