package org.javace.oportunidades;

import java.util.List;

public class Indicacao {

	protected List<Produto> produtos;
	protected Cliente cliente;
	protected Contato contato;
	protected Colaborador colaborador;
	
	public Indicacao(List<Produto> produtos, Cliente cliente, Contato contato, Colaborador colaborador) {
		this.produtos = produtos;
		this.cliente = cliente;
		this.contato = contato;
		this.colaborador = colaborador;
	}

}
