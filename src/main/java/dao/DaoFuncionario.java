package dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import model.Funcionario;

public class DaoFuncionario extends Dao<Funcionario> {
    public DaoFuncionario() {
        super(Funcionario.class);
    }
    public void reajustarSalarios(double porcentagem) {
        List<Funcionario> funcionarios = listAll();
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            for (Funcionario funcionario : funcionarios) {
                BigDecimal salarioAtual = funcionario.getSalario();
                BigDecimal reajuste = salarioAtual.multiply(BigDecimal.valueOf(porcentagem / 100));
                BigDecimal novoSalario = salarioAtual.add(reajuste);
                funcionario.setSalario(novoSalario);
                em.merge(funcionario);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}