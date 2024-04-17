package br.com.alura.challenge.useCase.user;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.alura.challenge.domain.User;
import br.com.alura.challenge.repository.UserRepository;
import br.com.alura.challenge.shared.UseCase;
import br.com.alura.challenge.useCase.user.dto.GetUserDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetUserByUsername implements UseCase< String, GetUserDTO > {

	private final UserRepository userRepository;

	@Override
	public GetUserDTO execute( String username ) throws NoSuchElementException {
		Optional< User > userOpt = userRepository.findByUsername( username );
		if ( userOpt.isEmpty() ) {
			throw new NoSuchElementException( "User not found" );
		}
		User user = userOpt.get();
		return new GetUserDTO( user.getUsername(), user.getEmail(), user.getRoles() );
	}

}
