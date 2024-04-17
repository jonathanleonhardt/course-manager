package br.com.alura.challenge.useCase.user.dto;

import java.util.Set;

public record GetUserDTO( String name, String email, Set< UserRolesEnum > roles ) {
	
}
