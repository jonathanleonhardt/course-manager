package br.com.alura.gateway;

import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.user.useCase.CreateUser;
import br.com.alura.user.useCase.GetUserByUsername;
import br.com.alura.user.useCase.dto.CreateUserDTO;
import br.com.alura.user.useCase.dto.GetUserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping( "/api/user" )
public class UserResource {

	private final GetUserByUsername getUserByUsername;
	private final CreateUser createUser;

	@PostMapping( "/create" )
	public ResponseEntity< GetUserDTO > createUser( @Valid @RequestBody CreateUserDTO user ) {
		try {
			return ResponseEntity.ok( this.createUser.execute( user ) );
		} catch ( Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping( "/{username}" )
	public ResponseEntity< GetUserDTO > getUserByUsername( @PathVariable String username ) {
		try {
			return ResponseEntity.ok( this.getUserByUsername.execute( username ) );
		} catch ( NoSuchElementException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
