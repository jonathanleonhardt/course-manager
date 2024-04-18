package br.com.alura.user.useCase;

import org.springframework.stereotype.Service;

import br.com.alura.core.shared.UseCase;
import br.com.alura.user.domain.User;
import br.com.alura.user.repository.UserRepository;
import br.com.alura.user.useCase.dto.CreateUserDTO;
import br.com.alura.user.useCase.dto.GetUserDTO;
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
