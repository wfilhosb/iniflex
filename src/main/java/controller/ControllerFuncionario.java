package controller;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import dao.DaoFuncionario;
import model.Funcionario;

public class ControllerFuncionario {
	private DaoFuncionario daoFuncionario;

	public ControllerFuncionario() {
		this.daoFuncionario = new DaoFuncionario();
	}

//INSERÇÃO EM MASSA DOS USUÁRIOS VIA CSV
	public void inserirFuncionariosEmMassa(String caminhoCsv) {
		List<Funcionario> funcionarios = lerFuncionariosDoCsv(caminhoCsv);
		for (Funcionario funcionario : funcionarios) {
			daoFuncionario.create(funcionario);
		}
	}

	private List<Funcionario> lerFuncionariosDoCsv(String caminhoCsv) {
		List<Funcionario> funcionarios = new ArrayList<>();
		try (CSVReader reader = new CSVReader(new FileReader(caminhoCsv))) {
			List<String[]> linhas = reader.readAll();
			SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

			for (int i = 1; i < linhas.size(); i++) {
				String[] linha = linhas.get(i);

				if (linha.length < 4) {
					System.out.println("Linha inválida (menos de 4 colunas): " + String.join(";", linha));
					continue;
				}

				String nome = linha[0];
				Date dataNascimento = formatoData.parse(linha[1]);
				BigDecimal salario = new BigDecimal(linha[2]);
				String funcao = linha[3];

				Funcionario funcionario = new Funcionario();
				funcionario.setNome(nome);
				funcionario.setDataNascimento(dataNascimento);
				funcionario.setSalario(salario);
				funcionario.setFuncao(funcao);

				funcionarios.add(funcionario);
			}
		} catch (IOException | CsvException | ParseException e) {
			e.printStackTrace();
		}
		return funcionarios;
	}

//REMOÇÃO DE UM FUNCIONÁRIO PELO ID
	public void removerFuncionarioPorId(int id) {
		DaoFuncionario daoFuncionario = new DaoFuncionario();
		daoFuncionario.deleteById(id);
		System.out.println("Funcionário removido com sucesso!");
	}
}
