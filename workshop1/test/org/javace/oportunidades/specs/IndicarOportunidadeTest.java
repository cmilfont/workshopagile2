package org.javace.oportunidades.specs;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.javace.oportunidades.Cliente;
import org.javace.oportunidades.Colaborador;
import org.javace.oportunidades.Contato;
import org.javace.oportunidades.Empresa;
import org.javace.oportunidades.Indicacao;
import org.javace.oportunidades.IndicarOportunidade;
import org.javace.oportunidades.Oportunidade;
import org.javace.oportunidades.Produto;
import org.javace.oportunidades.ProdutosJaIndicados;
import org.javace.oportunidades.Saldo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;

public class IndicarOportunidadeTest {
	
	Colaborador colaborador;
	Produto consultoria;
	Produto treinamento;
	Produto design;
	List<Produto> produtos;
	Cliente cliente;
	Contato contato;
	ProdutosJaIndicados produtosJaIndicados;
	IndicarOportunidade indicar;
	Indicacao indicacao;
	List<Oportunidade> oportunidades;
	Saldo saldo;
	
	@Before
	public void setUp() throws Exception {
		
		colaborador = new Colaborador() { { this.nome = "Milfont"; } };
		
		consultoria = new Produto() {
			{
				this.empresa = new Empresa() {{this.nome = "Milfont Consulting";}};
				this.pontos = 90;
				this.nome = "Consultoria";
			}
		};
		
		treinamento = new Produto() {
			{
				this.empresa = new Empresa() {{this.nome = "TriadWorks";}};
				this.pontos = 50;
				this.nome = "Treinamento";
			}
		};
		
		design = new Produto() {
			{
				this.empresa = new Empresa() {{this.nome = "PorDoTom";}};
				this.pontos = 49;
				this.nome = "Design";
			}
		};

		produtos = new ArrayList<Produto>();
		produtos.add(consultoria);
		produtos.add(treinamento);
		produtos.add(design);
		
		cliente = new Cliente("Luthor Corp");
		
		produtosJaIndicados = mock( ProdutosJaIndicados.class );
		when(produtosJaIndicados.jaExisteDentroDoPeriodoValido(treinamento)).thenReturn(Boolean.FALSE);
		when(produtosJaIndicados.jaExisteDentroDoPeriodoValido(design)).thenReturn(Boolean.FALSE);
		when(produtosJaIndicados.jaExisteDentroDoPeriodoValido(consultoria)).thenReturn(Boolean.TRUE);
		
		
		Session session = mock(Session.class);
		Oportunidade opt = mock(Oportunidade.class);
		doAnswer(new Answer() {
			long id = 0;
			public Object answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				Oportunidade oportunidade = (Oportunidade) invocation.getMock();
				oportunidade.setId(id++);
				return null;
			}
		}).when(session).persist(opt);
		
		contato = new Contato() {{ this.nome = "Lionel Luthor"; }};
		indicar = new IndicarOportunidade(produtosJaIndicados, session, new MockResult());
		indicacao = new Indicacao(produtos, cliente, contato, colaborador);
		oportunidades = indicar.comoColaborador(indicacao);
		saldo = new Saldo(colaborador);
	}

	@After
	public void tearDown() throws Exception {
	}

	
	/**
	 * Para ganhar pontos e comissão
	 * Como um colaborador
	 * Eu gostaria de fazer uma indicação de cliente para um produto


	 * Cenário de indicação com oportunidades menores que 90 dias

	 * Dado empresas cadastras: Milfont Consulting, TriadWorks e PorDoTom
	 * E os produtos "Consultoria" com 90 de pontuação, "Treinamento" com 50 de pontuação
	 * E "Design" com 49 de pontuação, respectivos dessas empresas
	 * E uma oportunidade do produto Consultoria já indicada há 60 dias para o cliente LuthorCorp
	 * Por outro colaborador
	 * Quando eu informar os produtos "Consultoria", "Treinamento" e "Design" para a LuthorCorp
	 * E seu contato Lionel Luthor
	 */
	@Test
	public void comoColaborador() {
		 /** 
		 * Então eu deverei ver minha indicação dos produtos "Treinamento" e "Design" como pendentes
		 * E a indicação de "Consultoria" como rejeitada
		 * E meus saldo com 99 pontos
		 */
		assertTrue("3 oportunidades geradas", oportunidades.size() == 3);
	}
	
	@Test
	public void vereiOportunidadeRejeitada() {
		assertTrue("Oportunidade Rejeitada", filtrarOportunidade(consultoria).isRejeitado() );
	}
	
	@Test
	public void vereiOportunidadesPendentes() {
		assertTrue("Oportunidade Pendente", filtrarOportunidade(treinamento).isPendente() );
		assertTrue("Oportunidade Pendente", filtrarOportunidade(design).isPendente() );
	}
	
	@Test
	public void vereiMeuSaldo() {
		assertTrue("Saldo deve ser 99 pontos", saldo.atual() == 99);
	}
	
	private Oportunidade filtrarOportunidade(Produto produto) {
		Oportunidade oportunidade = null;
		for (Oportunidade op : oportunidades) {
			if(op.getProduto().equals(produto) ) {
				oportunidade = op;
			}
		}
		return oportunidade;
	}

}
