package br.com.alura.challenge.useCase.user;

import org.springframework.stereotype.Service;

import br.com.alura.challenge.domain.User;
import br.com.alura.challenge.repository.UserRepository;
import br.com.alura.challenge.shared.UseCase;
import br.com.alura.challenge.useCase.user.dto.CreateUserDTO;
import br.com.alura.challenge.useCase.user.dto.GetUserDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateUser implements UseCase< CreateUserDTO, GetUserDTO > {

	private final UserRepository userRepository;

	@Override
	public GetUserDTO execute( CreateUserDTO dto ) throws Exception {
		User user = User.builder()
				.withName( dto.name() )
				.withUsername( dto.username() )
				.withEmail( dto.email() )
				.withPassword( dto.email() )
				.withRoles( dto.roles() )
				.build();
		user = this.userRepository.save( user );
		return new GetUserDTO( user.getName(), user.getEmail(), user.getRoles() );
	}
	
}
