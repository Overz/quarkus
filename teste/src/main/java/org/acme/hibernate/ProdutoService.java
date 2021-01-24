package org.acme.hibernate;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class ProdutoService {
	/**
	 * Quarkus recomenda não utilizar `private` ou etc em atributos
	 * com a Annotaion @Inject, pois ele evita de fazer reflection
	 * para fazer a injeção
	 */
	@Inject
	EntityManager em;

	public List<ProdutoVO> getProdutos() {
		return em
			.createQuery("select p from produto p", ProdutoVO.class)
			.getResultList();
	}

	public void addProduto(ProdutoVO p) {}
}
