package modelo.dao;

import java.util.List;

import modelo.entidade.Departamento;
import modelo.entidade.Vendedor;

public interface VendedorDao {

	void inserir(Vendedor obj);

	void atualizar(Vendedor obj);

	void deletarId(Integer id);

	Vendedor acharId(Integer id);

	List<Vendedor> acharTudo();

	List<Vendedor> acharPorDepartamento(Departamento departamento);

}
