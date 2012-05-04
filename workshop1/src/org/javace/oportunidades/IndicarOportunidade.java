package org.javace.oportunidades;

import static br.com.caelum.vraptor.view.Results.json;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class IndicarOportunidade {

	Session session;
	private final Result result;
	ProdutosJaIndicados produtosJaIndicados;
	
	public IndicarOportunidade(ProdutosJaIndicados produtosJaIndicados, Session session, Result result) {
		this.produtosJaIndicados = produtosJaIndicados;
		this.session = session;
		this.result = result;
	}

	@Post
	@Consumes
	public List<Oportunidade> comoColaborador(Indicacao indicacao) {
		List<Oportunidade> oportunidades = new ArrayList<Oportunidade>();
		if(indicacao.produtos != null) {
			for (Produto produto : indicacao.produtos) {
				Oportunidade oportunidade = new Oportunidade();
				oportunidade.produto = produto;
				oportunidade.cliente = indicacao.cliente;
				oportunidade.contato = indicacao.contato;
				oportunidade.entraComoRejeitada(produtosJaIndicados);
				oportunidade.gerarPontos(indicacao.colaborador);
				oportunidades.add(oportunidade);
				session.persist(oportunidade);
			}
		}
		this.result.use(json()).withoutRoot().from(oportunidades).serialize();
		return oportunidades;
	}

}
