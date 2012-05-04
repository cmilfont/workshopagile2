package org.milfont.oportunidades;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.util.List;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;


public class IndicacaoOportunidades {

	Contato contato;
	List<Produto> produtos;
	Cliente cliente;
	Indicacao indicacao;
	IndicarOportunidades indicar;
	List<Oportunidade> oportunidades;
	Produto consultoria;
	
	@Before
	public void setUp() throws Exception {
		
		consultoria = new Produto();
		consultoria.nome = "Consultoria";
		Produto treinamento = new Produto();
		treinamento.nome = "Treinamento";
		Produto design = new Produto();
		design.nome = "Design";
		produtos.add(design);
		produtos.add(treinamento);
		produtos.add(consultoria);
		
		cliente = new Cliente();
		
		OportunidadesJaInformados oportunidadesJaInformados = mock(OportunidadesJaInformados.class);
		when(oportunidadesJaInformados.jaExisteNoPeriodoValido(consultoria)).thenReturn(true);
		when(oportunidadesJaInformados.jaExisteNoPeriodoValido(design)).thenReturn(false);
		when(oportunidadesJaInformados.jaExisteNoPeriodoValido(treinamento)).thenReturn(false);
		
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
		
		indicacao = new Indicacao(contato, produtos, cliente);
		indicar = new IndicarOportunidades(indicacao, session,  new MockResult());
		
		oportunidades = indicar.comoColaborador();
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
		 * Então eu deverei ver minha indicação dos produtos "Treinamento" e "Design" como pendentes
		 * E a indicação de "Consultoria" como rejeitada
		 * E meus saldo com 99 pontos
	 */

	@Test
	public void vereiOportunidadesGeradas() {
		assertTrue("3 oportunidades informadas", oportunidades.size() == 3);
	}
	
	@Test
	public void vereiOportunidadesPendentes() {
		for(Oportunidade oportunidade: oportunidades) {
			assertTrue("É pendente", oportunidade.isPendente());			
		}
	}
	
	@Test
	public void vereiOportunidadeRejeitada() {
		Oportunidade oportunidadeDeConsultoria = filtrarOportunidade(consultoria);
		assertTrue("Consultoria informada entrou rejeitada", oportunidadeDeConsultoria.isRejeitada() );
	}
	
	@Test
	public void vereiMeuSaldo() {
		
	}
	
	private Oportunidade filtrarOportunidade(Produto produto) {
		Oportunidade oportunidade = null;
		for(Oportunidade opt: oportunidades) {
			if(opt.getProduto().equals(produto)) {
				oportunidade = opt;
			}
		}
		return oportunidade;
	}

}
