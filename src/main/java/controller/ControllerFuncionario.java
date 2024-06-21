package controller;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import dao.DaoFuncionario;
import model.Funcionario;

public class ControllerFuncionario {
	private DaoFuncionario daoFuncionario;

	public ControllerFuncionario() {
		this.daoFuncionario = new DaoFuncionario();
	}

	// INSERÇÃO EM MASSA DOS USUÁRIOS VIA CSV
	public void inserirFuncionariosEmMassa(String caminhoCsv) {
		List<Funcionario> funcionarios = lerFuncionariosDoCsv(caminhoCsv);
		for (Funcionario funcionario : funcionarios) {
			daoFuncionario.create(funcionario);
		}
		System.out.println("Arquivo CSV de Funcionários importado com sucesso!");
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

	// REMOÇÃO DE UM FUNCIONÁRIO PELO ID
	public void removerFuncionarioPorId(int id) {
		DaoFuncionario daoFuncionario = new DaoFuncionario();
		daoFuncionario.deleteById(id);
		System.out.println("Funcionário removido com sucesso!");
	}

	// LISTAGEM COMPLETA DOS FUNCIONARIOS
	public void listarTodosFuncionariosFormatado() {
		DaoFuncionario daoFuncionario = new DaoFuncionario();
		List<Funcionario> funcionarios = daoFuncionario.listAll();
		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
		NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

		for (Funcionario funcionario : funcionarios) {
			String dataNascimento = formatoData.format(funcionario.getDataNascimento());
			String salario = formatoMoeda.format(funcionario.getSalario());
			System.out.println("Nome: " + funcionario.getNome() + ", Data de Nascimento: " + dataNascimento
					+ ", Salário: " + salario + ", Função: " + funcionario.getFuncao());
		}
	}

	// AGRUPAR OS FUNCIONÁRIOS POR FUNÇÃO USANDO O MAP
	public void agruparFuncionariosPorFuncao() {
		DaoFuncionario daoFuncionario = new DaoFuncionario();
		List<Funcionario> funcionarios = daoFuncionario.listAll();
		Map<String, List<String>> map = new HashMap<>();

		for (Funcionario funcionario : funcionarios) {
			map.computeIfAbsent(funcionario.getFuncao(), k -> new ArrayList<>()).add(funcionario.getNome());
		}

		for (Map.Entry<String, List<String>> entry : map.entrySet()) {
			System.out.println("Função: " + entry.getKey() + " - Funcionários: " + entry.getValue());
		}
	}

	// LISTAGEM DE ANIVERSARIANTES POR MESES
	public void listarAniversariantesPorMes(List<Integer> meses) {
		DaoFuncionario daoFuncionario = new DaoFuncionario();
		List<Funcionario> funcionarios = daoFuncionario.listAll();
		Map<Integer, List<String>> aniversariantesPorMes = new HashMap<>();
		SimpleDateFormat mesFormat = new SimpleDateFormat("MM");
		SimpleDateFormat nomeMesFormat = new SimpleDateFormat("MMMM", new Locale("pt", "BR"));

		for (Funcionario funcionario : funcionarios) {
			int mesNascimento = Integer.parseInt(mesFormat.format(funcionario.getDataNascimento()));
			if (meses.contains(mesNascimento)) {
				aniversariantesPorMes.computeIfAbsent(mesNascimento, k -> new ArrayList<>()).add(funcionario.getNome());
			}
		}

		for (Map.Entry<Integer, List<String>> entry : aniversariantesPorMes.entrySet()) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MONTH, entry.getKey() - 1);
			String nomeMes = nomeMesFormat.format(cal.getTime());
			System.out.println("Mês: " + nomeMes);
			for (String nome : entry.getValue()) {
				System.out.println(" - " + nome);
			}
		}
	}

	// LISTAGEM DE FUNCIONARIO MAIS VELHO, OU FUNCIONARIOS
	public void listarFuncionariosMaisVelhos() {
		DaoFuncionario daoFuncionario = new DaoFuncionario();
		List<Funcionario> funcionarios = daoFuncionario.listAll();
		if (funcionarios.isEmpty()) {
			System.out.println("Nenhum funcionário encontrado.");
			return;
		}

		Date menorDataNascimento = Collections.min(funcionarios, Comparator.comparing(Funcionario::getDataNascimento))
				.getDataNascimento();

		List<Funcionario> funcionariosMaisVelhos = new ArrayList<>();
		for (Funcionario funcionario : funcionarios) {
			if (funcionario.getDataNascimento().equals(menorDataNascimento)) {
				funcionariosMaisVelhos.add(funcionario);
			}
		}

		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
		for (Funcionario funcionario : funcionariosMaisVelhos) {
			int idade = calcularIdade(funcionario.getDataNascimento());
			System.out.println("Nome: " + funcionario.getNome() + ", Idade: " + idade);
		}
	}

	private int calcularIdade(Date dataNascimento) {
		Calendar dataNasc = Calendar.getInstance();
		dataNasc.setTime(dataNascimento);
		Calendar hoje = Calendar.getInstance();
		int idade = hoje.get(Calendar.YEAR) - dataNasc.get(Calendar.YEAR);
		if (hoje.get(Calendar.DAY_OF_YEAR) < dataNasc.get(Calendar.DAY_OF_YEAR)) {
			idade--;
		}
		return idade;
	}

	// LISTAGEM POR ORDEM ALFABETICA
	public void listarFuncionariosOrdemAlfabetica() {
		DaoFuncionario daoFuncionario = new DaoFuncionario();
		List<Funcionario> funcionarios = daoFuncionario.listAll();
		Collections.sort(funcionarios, Comparator.comparing(Funcionario::getNome));

		for (Funcionario funcionario : funcionarios) {
			System.out.println("Nome: " + funcionario.getNome());
		}
	}
	
	//SOMA DE TODOS OS SALÁRIOS
	public void somaTodosSalarios() {
		DaoFuncionario daoFuncionario = new DaoFuncionario();
        List<Funcionario> funcionarios = daoFuncionario.listAll();
        BigDecimal somaSalarios = BigDecimal.ZERO;
        
        for (Funcionario funcionario : funcionarios) {
            somaSalarios = somaSalarios.add(funcionario.getSalario());
        }

        System.out.println("Soma de todos os salários: " + somaSalarios);
    }
	
	//LISTAGEM DE SALÁRIOS MÍNIMOS QUE CADA PESSOA RECEBE.
	public void listarSalariosMinimos(BigDecimal salarioMinimo) {
		DaoFuncionario daoFuncionario = new DaoFuncionario();
        List<Funcionario> funcionarios = daoFuncionario.listAll();
        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        
        System.out.println("Nome | Salário | Salários Mínimos");
        for (Funcionario funcionario : funcionarios) {
            BigDecimal salario = funcionario.getSalario();
            BigDecimal salariosMinimos = salario.divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_UP);
            String salarioFormatado = formatoMoeda.format(salario);
            System.out.println(funcionario.getNome() + " | " + salarioFormatado + " | " + salariosMinimos);
        }
    }
}







