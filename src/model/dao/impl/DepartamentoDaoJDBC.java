package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartamentoDao;
import model.entities.Departamento;

public class DepartamentoDaoJDBC implements DepartamentoDao{

	private Connection conn;
	
	public DepartamentoDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void inserir(Departamento obj) {

		PreparedStatement st = null;

		try {
			st = conn.prepareStatement(
					"INSERT INTO department (name) "
					+ "VALUES(?) ",
					Statement.RETURN_GENERATED_KEYS
					);
			
			st.setString(1, obj.getName());
			
			int linhasAfetadas = st.executeUpdate();
			
			if(linhasAfetadas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
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
	public void atualizar(Departamento obj) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"UPDATE department "
					+ "SET Name = ? "
					+ "WHERE Id = ? "
					);
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void deleteId(Integer id) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("DELETE FROM department WHERE Id = ?");
			
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
	public Departamento buscarId(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT department.* " 
					+ "FROM department "
					+ "WHERE department.Id = ? "
					);
			
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if (rs.next()) {
				Departamento dep = instanciacaoDepartamento(rs);
				return dep;
			}
			return null;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
			// não fechar essa connection
		}
	}

	@Override
	public List<Departamento> buscarTodos() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT department.* " 
					+ "FROM department "
					+ "ORDER BY Name "
					);
			
			rs = st.executeQuery();
			List<Departamento> list = new ArrayList<>();
			
			while(rs.next()) {
				Departamento dep = instanciacaoDepartamento(rs);
				list.add(dep);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	private Departamento instanciacaoDepartamento(ResultSet rs) throws SQLException {
		Departamento dep = new Departamento();
		dep.setId(rs.getInt("department.Id"));
		dep.setName(rs.getString("Name"));
		return dep;
	}

}
