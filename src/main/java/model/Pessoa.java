package model;

import lombok.Data;

import java.util.Date;

import javax.persistence.*;

@Data
@Entity
public class Pessoa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codigo;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private Date dataNascimento;
}
