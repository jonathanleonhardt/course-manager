package br.com.alura.user.useCase.dto;

import br.com.alura.core.enums.UserRolesEnum;
import br.com.alura.core.shared.Patterns;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateUserDTO(
		@NotBlank
		String name,
		@Pattern(regexp = Patterns.USERNAME, message = "Invalid Username")
		@Size( max = 20, message = "You cant use more than 20 caracteres in username" )
		String username,
		@Pattern(regexp = Patterns.EMAIL, message = "Invalid Email")
		String email,
		@NotBlank
		String password,
		@NotBlank
		UserRolesEnum role ) {
}
