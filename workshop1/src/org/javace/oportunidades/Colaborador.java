package org.javace.oportunidades;

public class Colaborador {

	private int saldoInterno;
	protected String nome;

	public void receberPontos(Integer pontos) {
		this.saldoInterno += pontos;
	}
	
	public Integer saldo() {
		return this.saldoInterno;
	}
	
}
