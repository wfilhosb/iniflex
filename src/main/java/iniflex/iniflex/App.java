package iniflex.iniflex;

import java.math.BigDecimal;
import java.util.Arrays;

import controller.ControllerFuncionario;
import dao.DaoFuncionario;

public class App {
	public static void main(String[] args) {
		ControllerFuncionario controllerFuncionario = new ControllerFuncionario();
		DaoFuncionario daoFuncionario = new DaoFuncionario();
		// 3.1 - INSERÇÃO DETODOS OS FUNCIONÁRIOS DA TABELA DO DESAFIO QUE FOI EXPORTADA EM CSV
		//controllerFuncionario.inserirFuncionariosEmMassa("src/cadastroDeFuncionarios.csv");

		//3.2 - REMOVER O FUNCIONÁRIO JOÃO DA LISTA MELHOR SER FEITO POR CÓDIGO JÁ QUE É POSSÍVEL DESCORBIR ATRAVES DO LISTAR TODOS
		//controllerFuncionario.removerFuncionarioPorId(2);
		
		//3.3 - LISTAR TODOS OS FUNCIONARIOS FORMATANDO A DATA E O SALÁRIO PARA O PADRÃO BR
		//controllerFuncionario.listarTodosFuncionariosFormatado();
		
		//3.4 - PASSAR UMA PORCENTAGEM POSITIVA OU NEGATIVA PARA O MÉTODO E ESTA PORCENTAGEM SERÁ REJUSTADA NO SALÁRIO DE TODOS FUNCIONARIOS
		//daoFuncionario.reajustarSalarios(10);
		
		//3.5 & 3.6 - AGRUPAR FUNCIONÁRIOS POR FUNÇÃO E IMPRIMIR USANDO MAP.
		//controllerFuncionario.agruparFuncionariosPorFuncao();
		
		//3.8 - IMPRIMIR ANIVERSARIANTES DO MÊS PASSANDO UM OU MAIS MESES DO ANO;
		//controllerFuncionario.listarAniversariantesPorMes(Arrays.asList(10, 12));
		
		//3.9 - IMPRIMIR FUNCIONARIO, OU FUNCIONARIOS SE DATA DE NASCIMENTO IGUAIS, MAIS VELHO DE TODOS.
		//controllerFuncionario.listarFuncionariosMaisVelhos();
		
		//3.10 - LISTA DE FUNCIONARIOS POR ORDEM ALFABÉTICA
		//controllerFuncionario.listarFuncionariosOrdemAlfabetica();
		
		//3.11 - SOMATÓRIO DE TODOS OS SALÁRIOS
		//controllerFuncionario.somaTodosSalarios();
		
		//3.12 - LISTAR QUANTOS SALÁRIOS MÍNIMOS CADA PESSOA RECEBE, TEM QUE PASSAR O VALOR DO SALÁRIO MÍNIMO ATUAL PARA A FUNÇÃO
		//controllerFuncionario.listarSalariosMinimos(new BigDecimal(1212.00));
	}

}