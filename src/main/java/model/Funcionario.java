package model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Funcionario extends Pessoa {
	
	@Column(nullable = false)
	private BigDecimal salario;
	
	@Column(nullable = false)
	private String funcao;
}
