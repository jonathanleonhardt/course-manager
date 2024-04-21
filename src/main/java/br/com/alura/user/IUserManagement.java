package br.com.alura.user;

import java.util.NoSuchElementException;

import br.com.alura.core.dto.CreateUserDTO;
import br.com.alura.core.dto.GetUserDTO;

public interface IUserManagement {

	GetUserDTO createUser( CreateUserDTO dto ) throws IllegalArgumentException;

	GetUserDTO getUserByUsername( String username ) throws NoSuchElementException;

}
