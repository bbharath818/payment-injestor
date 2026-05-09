package com.lloyds.payments.ingestor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lloyds.payments.ingestor.entity.AccountEntity;
import com.lloyds.payments.ingestor.repository.AccountRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class PaymentsIngestorApplication {


	private final AccountRepository accountRepository;


    public static void main(String[] args) {
		SpringApplication.run(PaymentsIngestorApplication.class, args);
	}

	@PostConstruct
	public void init() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		InputStream inputStream = getClass().getResourceAsStream("/data/accounts.json");
		if (inputStream != null) {
			List<AccountEntity> accounts = mapper.readValue(inputStream, new TypeReference<>() {});
			accountRepository.saveAll(accounts);
			log.info("✅ Accounts loaded: {} records saved to H2.", accounts.size());
		} else {
			log.warn("⚠️ accounts.json file not found!");
		}
	}
}
