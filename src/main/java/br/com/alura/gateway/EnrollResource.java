package br.com.alura.gateway;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.core.dto.CreateEnrollDTO;
import br.com.alura.core.dto.GetEnrollDTO;
import br.com.alura.enroll.useCase.CreateEnroll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping( "/api/enroll" )
public class EnrollResource {

	private final CreateEnroll createEnroll;

	@PostMapping( "/create" )
	public ResponseEntity< GetEnrollDTO > createUser( @Valid @RequestBody CreateEnrollDTO enroll ) {
		try {
			return ResponseEntity.ok( this.createEnroll.execute( enroll ) );
		} catch ( Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
}
