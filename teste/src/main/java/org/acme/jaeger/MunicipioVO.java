package org.acme.jaeger;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "municipio")
public class MunicipioVO implements Serializable {
	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "nome")
	private String nome;
}
