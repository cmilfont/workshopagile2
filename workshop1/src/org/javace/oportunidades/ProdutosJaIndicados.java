package org.javace.oportunidades;

import br.com.caelum.vraptor.ioc.Component;

@Component
public class ProdutosJaIndicados {
	
	public ProdutosJaIndicados() {}

	public Boolean jaExisteDentroDoPeriodoValido(Produto produto) {
		return Boolean.FALSE;
	}
	
}
