package br.com.alura.gateway;

import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.core.dto.CreateUserDTO;
import br.com.alura.core.dto.GetUserDTO;
import br.com.alura.user.IUserManagement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping( "/api/user" )
public class UserResource {

	private final IUserManagement userManagement;

	@PostMapping( "/create" )
	public ResponseEntity< GetUserDTO > createUser( @Valid @RequestBody CreateUserDTO user ) {
		try {
			return ResponseEntity.ok( this.userManagement.createUser( user ) );
		} catch ( Exception e ) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping( "/info/{username}" )
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity< GetUserDTO > getUserByUsername( @PathVariable String username ) {
		try {
			return ResponseEntity.ok( this.userManagement.getUserByUsername( username ) );
		} catch ( NoSuchElementException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
