package br.com.alura.enroll;

import java.io.NotActiveException;
import java.util.NoSuchElementException;

import br.com.alura.core.dto.CreateEnrollDTO;
import br.com.alura.core.dto.GetEnrollDTO;

public interface IEnrollManagement {

	GetEnrollDTO createEnroll( CreateEnrollDTO dto ) throws NotActiveException, NoSuchElementException;

}
