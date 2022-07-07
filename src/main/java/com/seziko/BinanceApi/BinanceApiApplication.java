package com.seziko.BinanceApi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seziko.BinanceApi.bussiness.BinanceManagement;
import com.seziko.BinanceApi.entities.Binance;
import jdk.internal.org.objectweb.asm.TypeReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import sun.tools.jar.CommandLine;

import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class BinanceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BinanceApiApplication.class, args);
	}
	};
