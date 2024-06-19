package model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class Funcionario extends Pessoa {
	private BigDecimal salario;
	private String funcao;
}
