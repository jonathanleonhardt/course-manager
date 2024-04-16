package br.com.alura.challenge.api;

import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.challenge.useCase.user.GetUserByUsernameUseCase;
import br.com.alura.challenge.useCase.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping( "/api/user" )
public class UserResource {

	private final GetUserByUsernameUseCase getUserByUsername;

	@GetMapping( "/{username}" )
	public ResponseEntity< UserDTO > getUserByUsername( @PathVariable String username ) {
		try {
			return ResponseEntity.ok( this.getUserByUsername.execute( username ) );
		} catch ( NoSuchElementException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
