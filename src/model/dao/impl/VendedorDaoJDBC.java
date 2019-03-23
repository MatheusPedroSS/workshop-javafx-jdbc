package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.VendedorDao;
import model.entities.Departamento;
import model.entities.Vendedor;

public class VendedorDaoJDBC implements VendedorDao {

	private Connection conn;

	public VendedorDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void inserir(Vendedor obj) {
		
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, Departmentid) "
					+ "VALUES "
					+ "(?,?,?,?,?) ",
					Statement.RETURN_GENERATED_KEYS
					);
			
			st.setString(1, obj.getNome());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getDataAniversario().getTime()));
			st.setDouble(4, obj.getSalarioBase());
			st.setInt(5, obj.getDepartamento().getId());
			
			int linhasAfetadas = st.executeUpdate();
			
			if (linhasAfetadas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeStatement(st);
			}
			else {
				throw new DbException("Error inesperado! nenuma linha afetada!");
			}
		}
		catch (SQLException e) {
			throw new DbException("Error: " + e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void atualizar(Vendedor obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE seller "
					+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, Departmentid = ? "
					+ "WHERE Id = ? ");
			
			st.setString(1, obj.getNome());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getDataAniversario().getTime()));
			st.setDouble(4, obj.getSalarioBase());
			st.setInt(5, obj.getDepartamento().getId());
			st.setInt(6, obj.getId());
			
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException("Error: " + e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deleteId(Integer id) {
		
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM seller WHERE Id = ?");
			
			st.setInt(1, id);
			
			int linhas = st.executeUpdate();
			
			if (linhas == 0) {
				throw new DbException("Id não encontrado");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public Vendedor buscarId(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as NomeDep " 
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id " 
					+ "WHERE seller.Id = ?");

			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Departamento dep = instanciacaoDepartamento(rs);

				Vendedor vend = instanciacaoVendedor(rs, dep);

				return vend;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
			// não fechar essa connection
		}

	}

	@Override
	public List<Vendedor> buscarTodos() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as NomeDep "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "ORDER BY Name "
					);
			
			rs = st.executeQuery();
			
			List<Vendedor> list = new ArrayList<Vendedor>();
			Map<Integer, Departamento> map = new HashMap<>();
			
			while (rs.next()) {
				
				Departamento dep = map.get(rs.getInt("DepartmentId"));
				
				if (dep == null ) {
					dep = instanciacaoDepartamento(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				Vendedor vend = instanciacaoVendedor(rs, dep);
				list.add(vend);
			}
			return list;
		} 
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
			// não fechar essa connection
		}
	}

	@Override
	public List<Vendedor> buscarDepartamento(Departamento departamento) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as NomeDep "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name "
					);

			st.setInt(1, departamento.getId());
			
			rs = st.executeQuery();
			
			List<Vendedor> list = new ArrayList<Vendedor>();
			Map<Integer, Departamento> map = new HashMap<>();
			
			while (rs.next()) {
				
				Departamento dep = map.get(rs.getInt("DepartmentId"));
				
				if (dep == null ) {
					dep = instanciacaoDepartamento(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				Vendedor vend = instanciacaoVendedor(rs, dep);
				list.add(vend);
			}
			return list;
		} 
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
			// não fechar essa connection
		}
	}

	private Vendedor instanciacaoVendedor(ResultSet rs, Departamento dep) throws SQLException {
		Vendedor vend = new Vendedor();
		vend.setId(rs.getInt("Id"));
		vend.setNome(rs.getString("Name"));
		vend.setEmail(rs.getString("Email"));
		vend.setSalarioBase(rs.getDouble("BaseSalary"));
		vend.setDataAniversario(rs.getDate("BirthDate"));
		vend.setDepartamento(dep);
		return vend;
	}

	private Departamento instanciacaoDepartamento(ResultSet rs) throws SQLException {
		Departamento dep = new Departamento();
		dep.setId(rs.getInt("Departmentid"));
		dep.setName(rs.getString("NomeDep"));
		return dep;
	}

}
