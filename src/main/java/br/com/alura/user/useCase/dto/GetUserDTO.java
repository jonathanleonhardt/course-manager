package br.com.alura.user.useCase.dto;

import java.util.Set;

import br.com.alura.core.enums.UserRolesEnum;

public record GetUserDTO( String name, String email, Set< UserRolesEnum > roles ) {
	
}
