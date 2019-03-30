package modelo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.Statement;

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
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(
					"INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) VALUES (?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, obj.getNome());
			pst.setString(2, obj.getEmail());
			pst.setDate(3, new java.sql.Date(obj.getDataNascimento().getTime()));
			pst.setDouble(4, obj.getSalario());
			pst.setInt(5, obj.getDepartamento().getId());

			int afetadas = pst.executeUpdate();
			if (afetadas > 0) {
				ResultSet rs = pst.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				} else {
					throw new BdException("Nenhuma linha afetada");
				}
				BD.closeResultSet(rs);
			}
		} catch (SQLException e) {
			throw new BdException(e.getMessage());
		} finally {
			BD.closeStatement(pst);
		}
	}

	@Override
	public void atualizar(Vendedor obj) {
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(
					"UPDATE seller set Name=?, Email=?, BirthDate=?, BaseSalary=?, DepartmentId=? WHERE Id=?",
					Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, obj.getNome());
			pst.setString(2, obj.getEmail());
			pst.setDate(3, new java.sql.Date(obj.getDataNascimento().getTime()));
			pst.setDouble(4, obj.getSalario());
			pst.setInt(5, obj.getDepartamento().getId());
			pst.setInt(6, obj.getId());

			pst.executeUpdate();
		} catch (SQLException e) {
			throw new BdException(e.getMessage());
		} finally {
			BD.closeStatement(pst);
		}
	}

	@Override
	public void deletarId(Integer id) {
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("DELETE FROM seller WHERE Id=?");
			pst.setInt(1, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			throw new BdException(e.getMessage());
		} finally {
			BD.closeStatement(pst);
		}
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
				Departamento dep = instanciarDepartamento(rs);
				Vendedor vend = instanciarVendedor(rs, dep);
				return vend;
			}
			return null;
		} catch (SQLException e) {
			throw new BdException(e.getMessage());
		} finally {
			BD.closeResultSet(rs);
			BD.closeStatement(pst);
		}
	}

	private Vendedor instanciarVendedor(ResultSet rs, Departamento dep) throws SQLException {
		Vendedor vend = new Vendedor();
		vend.setId(rs.getInt("Id"));
		vend.setNome(rs.getString("Name"));
		vend.setEmail(rs.getString("Email"));
		vend.setSalario(rs.getDouble("BaseSalary"));
		vend.setDataNascimento(rs.getDate("BirthDate"));
		vend.setDepartamento(dep);
		return vend;
	}

	private Departamento instanciarDepartamento(ResultSet rs) throws SQLException {
		Departamento dep = new Departamento();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setNome(rs.getString("DepName"));
		return dep;
	}

	@Override
	public List<Vendedor> acharTudo() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = con.prepareStatement(
					"Select seller.*, department.Name as DepName from seller INNER JOIN department on seller.DepartmentId=department.Id order by Name");
			rs = pst.executeQuery();

			List<Vendedor> lista = new ArrayList<>();
			Map<Integer, Departamento> maps = new HashMap<>();

			while (rs.next()) {
				Departamento dep = maps.get(rs.getInt("DepartmentId"));
				if (dep == null) {
					dep = instanciarDepartamento(rs);
					maps.put(dep.getId(), dep);
				}
				Vendedor vend = instanciarVendedor(rs, dep);
				lista.add(vend);
			}
			return lista;
		} catch (SQLException e) {
			throw new BdException(e.getMessage());
		} finally {
			BD.closeResultSet(rs);
			BD.closeStatement(pst);
		}
	}

	@Override
	public List<Vendedor> acharPorDepartamento(Departamento departamento) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = con.prepareStatement(
					"Select seller.*, department.Name as DepName from seller INNER JOIN department on seller.DepartmentId=department.Id where DepartmentId=? order by Name");

			pst.setInt(1, departamento.getId());
			rs = pst.executeQuery();

			List<Vendedor> lista = new ArrayList<>();
			Map<Integer, Departamento> maps = new HashMap<>();

			while (rs.next()) {
				Departamento dep = maps.get(rs.getInt("DepartmentId"));
				if (dep == null) {
					dep = instanciarDepartamento(rs);
					maps.put(dep.getId(), dep);
				}
				Vendedor vend = instanciarVendedor(rs, dep);
				lista.add(vend);
			}
			return lista;
		} catch (SQLException e) {
			throw new BdException(e.getMessage());
		} finally {
			BD.closeResultSet(rs);
			BD.closeStatement(pst);
		}
	}
}
