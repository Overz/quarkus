package org.acme.hibernate;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity(name = "produto")
public class ProdutoVO implements Serializable {
	@Id
	private Long id;

	private String nome;
}
