package br.com.alura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class AluraModulith {

	public static void main( String[] args ) {
		SpringApplication.run( AluraModulith.class, args );
	}

}
