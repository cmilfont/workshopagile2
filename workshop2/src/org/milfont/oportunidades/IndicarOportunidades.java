package org.milfont.oportunidades;

import java.util.ArrayList;
import java.util.List;
import static br.com.caelum.vraptor.view.Results.json;

import org.hibernate.Session;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class IndicarOportunidades {

	private Indicacao indicacao;
	private Session session;
	private final Result result;
	
	public IndicarOportunidades(Indicacao indicacao, Session session, Result result) {
		this.result = result;
	}

	@Get
	public List<Oportunidade> comoColaborador() {
		// TODO Auto-generated method stub
		List<Oportunidade> oportunidades = new ArrayList<Oportunidade>();
		
		session.save(null);
		
		this.result.use(json()).withoutRoot().from(oportunidades).serialize();
		return oportunidades;
	}

}
