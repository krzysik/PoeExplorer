package com.krzysztof.PoeExplorer;


import com.krzysztof.PoeExplorer.client.PoeNinjaClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class PoeExplorerApplication implements CommandLineRunner {


	private final PoeNinjaClient poeNinjaClient;

	public PoeExplorerApplication(PoeNinjaClient poeNinjaClient) {
		this.poeNinjaClient = poeNinjaClient;
	}

	public static void main(String[] args) {
		SpringApplication.run(PoeExplorerApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		var response = poeNinjaClient.getCurrencyPrices("Standard");

		response.lines().stream()
				.limit(10)
				.forEach(currency ->
						System.out.println(
								currency.id() + " = " + currency.primaryValue()
						)
				);
	}
}