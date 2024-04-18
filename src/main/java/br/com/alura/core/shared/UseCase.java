package br.com.alura.core.shared;

@FunctionalInterface
public interface UseCase< P, R > {

	R execute( P parameter ) throws Exception;

}
