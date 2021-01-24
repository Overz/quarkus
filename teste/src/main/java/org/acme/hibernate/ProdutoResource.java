package org.acme.hibernate;

import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.jboss.logging.Logger;

@Path("/produtos")
@Transactional
public class ProdutoResource {
	private static final Logger LOG = Logger.getLogger(ProdutoResource.class);

	@Inject
	ProdutoService ps;

	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public List<ProdutoVO> getProdutos() {
		LOG.info("Listando Produtos");
		return ps.getProdutos();
	}

	@POST
	@Consumes(value = MediaType.APPLICATION_JSON)
	public void addProdutos(ProdutoVO p) {
		LOG.info("Cadastrando Produtos");
		ps.addProduto(p);
	}
}
