package br.com.alura.user;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import br.com.alura.core.dto.CreateUserDTO;
import br.com.alura.core.dto.GetUserDTO;
import br.com.alura.persistence.user.useCase.CreateUser;
import br.com.alura.persistence.user.useCase.GetUserByUsername;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserManagement implements IUserManagement {

	private final CreateUser createUser;
	private final GetUserByUsername getUserByUsername;

	@Override
	public GetUserDTO createUser( CreateUserDTO dto ) throws IllegalArgumentException {
		return this.createUser.execute( dto );
	}

	@Override
	public GetUserDTO getUserByUsername( String username ) throws NoSuchElementException {
		return this.getUserByUsername.execute( username );
	}
	
}
