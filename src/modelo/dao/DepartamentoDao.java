package modelo.dao;

import java.util.List;

import modelo.entidade.Departamento;

public interface DepartamentoDao {

	void inserir(Departamento obj);

	void atualizar(Departamento obj);

	void deletarId(Integer id);

	Departamento acharId(Integer id);

	List<Departamento> acharTudo();

}
