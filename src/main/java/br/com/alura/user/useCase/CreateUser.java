package br.com.alura.user.useCase;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.alura.core.dto.CreateUserDTO;
import br.com.alura.core.dto.GetUserDTO;
import br.com.alura.core.shared.UseCase;
import br.com.alura.user.domain.Role;
import br.com.alura.user.domain.User;
import br.com.alura.user.repository.RoleRepository;
import br.com.alura.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateUser implements UseCase< CreateUserDTO, GetUserDTO > {

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public GetUserDTO execute( CreateUserDTO dto ) throws Exception {
		Role role = getRoleToPersist( dto );
		String hashedPassword = passwordEncoder.encode( dto.password() );
		User user = User.builder()
				.withName( dto.name() )
				.withUsername( dto.username() )
				.withEmail( dto.email() )
				.withPassword( hashedPassword )
				.withRole( role )
				.build();
		user = this.userRepository.save( user );
		return new GetUserDTO( user.getName(), user.getEmail(), user.getRole().getName() );
	}

	private Role getRoleToPersist( CreateUserDTO dto ) {
		Optional< Role > entity = this.roleRepository.findByName( dto.role().name() );
		if ( entity.isEmpty() ) {
			throw new IllegalArgumentException( "Role doesnt exist" );
		}
		return entity.get();
	}

}
