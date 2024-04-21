package br.com.alura.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

	private final UserDetailsService userDetailsService;

	@Bean
	public SecurityFilterChain filterChain( HttpSecurity http ) throws Exception {
		http.csrf( AbstractHttpConfigurer::disable )
				.authorizeHttpRequests(
						authorize -> authorize
								.requestMatchers( 
									"/api/user/create",
										"/v3/api-docs",
										"/swagger-ui/**",
										"/swagger-resources/**",
										"/*/swagger-resources/**",
										"/*/v3/api-docs" ).permitAll()
								.anyRequest().authenticated() )
				.httpBasic( Customizer.withDefaults() )
				.sessionManagement(
						httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
								.sessionCreationPolicy( SessionCreationPolicy.STATELESS ) );

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return this.userDetailsService;
	}

	@Bean
	public AuthenticationManager authenticationManager(
			UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder ) {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService( userDetailsService );
		authenticationProvider.setPasswordEncoder( passwordEncoder );

		ProviderManager providerManager = new ProviderManager( authenticationProvider );
		providerManager.setEraseCredentialsAfterAuthentication( false );

		return providerManager;
	}

}