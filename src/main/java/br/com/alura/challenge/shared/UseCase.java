package br.com.alura.challenge.shared;

@FunctionalInterface
public interface UseCase< P, R > {

	R execute( P parameter ) throws Exception;

}
