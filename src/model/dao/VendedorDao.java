package model.dao;

import java.util.List;

import model.entities.Departamento;
import model.entities.Vendedor;

public interface VendedorDao {

	void inserir(Vendedor obj);
	void atualizar(Vendedor obj);
	void deleteId(Integer id);
	Vendedor buscarId(Integer id);
	List<Vendedor> buscarTodos();
	List<Vendedor> buscarDepartamento(Departamento departamento);
	
}
