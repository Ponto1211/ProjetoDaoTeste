package modelo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import bd.BD;
import bd.BdException;
import modelo.dao.VendedorDao;
import modelo.entidade.Departamento;
import modelo.entidade.Vendedor;

public class VendedorDaoJDBC implements VendedorDao {

	private Connection con;

	public VendedorDaoJDBC(Connection con) {
		this.con = con;
	}

	@Override
	public void inserir(Vendedor obj) {

	}

	@Override
	public void atualizar(Vendedor obj) {

	}

	@Override
	public void deletarId(Integer id) {

	}

	@Override
	public Vendedor acharId(Integer id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = con.prepareStatement(
					"SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "WHERE seller.Id = ?");

			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next()) {
				Departamento dep = new Departamento();
				dep.setId(rs.getInt("DepartmentId"));
				dep.setNome(rs.getString("DepName"));
				Vendedor vend = new Vendedor();
				vend.setId(rs.getInt("Id"));
				vend.setNome(rs.getString("Name"));
				vend.setEmail(rs.getString("Email"));
				vend.setSalario(rs.getDouble("BaseSalary"));
				vend.setDataNascimento(rs.getDate("BirthDate"));
				vend.setDepartamento(dep);
				return vend;
			}
			return null;
		} catch (SQLException e) {
			throw new BdException(e.getMessage());
		}finally {
			BD.closeResultSet(rs);
			BD.closeStatement(pst);
		}

	}

	@Override
	public List<Vendedor> acharTudo() {
		return null;
	}

}
