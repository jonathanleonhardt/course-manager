package br.com.alura.challenge.shared;

@FunctionalInterface
public interface Usecase< P, R > {

	R execute( P parameter ) throws Exception;

}
