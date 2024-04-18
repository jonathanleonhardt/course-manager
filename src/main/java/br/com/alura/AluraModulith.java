package br.com.alura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication( exclude = { SecurityAutoConfiguration.class } )
public class AluraModulith {

	public static void main( String[] args ) {
		SpringApplication.run( AluraModulith.class, args );
	}

}
