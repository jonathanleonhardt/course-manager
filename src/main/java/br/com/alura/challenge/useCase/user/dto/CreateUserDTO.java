package br.com.alura.challenge.useCase.user.dto;

import java.util.Set;

import br.com.alura.challenge.shared.Patterns;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateUserDTO(
		String name,
		@Pattern(regexp = Patterns.USERNAME, message = "Invalid Username")
		@Size( max = 20, message = "You cant use more than 20 caracteres in username" )
		String username,
		@Pattern(regexp = Patterns.EMAIL, message = "Invalid Email")
		String email,
		String password,
		Set< UserRolesEnum > roles ) {
}
