package org.javace.oportunidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Oportunidade {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Transient
	protected Produto produto;
	
	public Produto getProduto() {
		return produto;
	}
	@Transient
	protected Cliente cliente;
	@Transient
	protected Contato contato;
	@Transient
	protected Colaborador colaborador;
	protected Situacao situacao;
	
	public boolean isRejeitado() {
		return situacao == Situacao.REJEITADO;
	}

	public boolean isPendente() {
		return situacao == Situacao.PENDENTE;
	}

	public void entraComoRejeitada(ProdutosJaIndicados produtosJaIndicados) {
		Boolean dentroDoPeriodoValido = produtosJaIndicados.jaExisteDentroDoPeriodoValido(this.produto);
		situacao = (dentroDoPeriodoValido) ? Situacao.REJEITADO : Situacao.PENDENTE;
	}

	public void gerarPontos(Colaborador colaborador) {
		this.colaborador = colaborador;
		if( isPendente() ) {
			this.colaborador.receberPontos(this.produto.pontos);
		}
	}

}
