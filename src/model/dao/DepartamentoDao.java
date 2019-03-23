package model.dao;

import java.util.List;

import model.entities.Departamento;

public interface DepartamentoDao {

	void inserir(Departamento obj);
	void atualizar(Departamento obj);
	void deleteId(Integer id);
	Departamento buscarId(Integer id);
	List<Departamento> buscarTodos();
	
}
