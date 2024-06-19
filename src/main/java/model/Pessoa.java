package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Pessoa {
	private String nome;
	private Date dataNascimento;
}
