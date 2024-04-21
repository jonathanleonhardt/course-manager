package br.com.alura.config;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.alura.persistence.user.domain.User;
import br.com.alura.persistence.user.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {
		Optional< User > userOpt = userRepository.findByUsername( username );
		if ( userOpt.isEmpty() ) {
			throw new UsernameNotFoundException( "User not found" );
		}
		User user = userOpt.get();
		return new org.springframework.security.core.userdetails.User(
				user.getUsername(), user.getPassword(), getAuthorities( user ) );
	}

	private Set< GrantedAuthority > getAuthorities( User user ) {
		Set< GrantedAuthority > authorities = new HashSet<>();
		authorities.add( new SimpleGrantedAuthority( user.getRole().getName() ) );
		return authorities;
	}

}
