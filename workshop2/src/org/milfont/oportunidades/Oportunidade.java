package org.milfont.oportunidades;

public class Oportunidade {

	private Long id;
	private Produto produto;
	
	public boolean isPendente() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isRejeitada() {
		// TODO Auto-generated method stub
		return false;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setId(long id) {
		this.id = id;
	}

}
