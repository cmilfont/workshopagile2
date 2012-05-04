package org.javace.oportunidades;

public class Saldo {
	
	private Colaborador colaborador;

	public Saldo(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public Integer atual() {
		return colaborador.saldo();
	}

}
