package br.com.alura.challenge.useCase.user;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.alura.challenge.domain.User;
import br.com.alura.challenge.repository.UserRepository;
import br.com.alura.challenge.shared.Usecase;
import br.com.alura.challenge.useCase.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetUserByUsernameUseCase implements Usecase< String, UserDTO > {

	private final UserRepository userRepository;

	@Override
	public UserDTO execute( String username ) throws NoSuchElementException {
		Optional< User > userOpt = userRepository.findByUsername( username );
		if ( userOpt.isEmpty() ) {
			throw new NoSuchElementException( "User not found" );
		}
		User user = userOpt.get();
		return new UserDTO( user.getUsername(), user.getEmail(), user.getRoles() );
	}
	
}
