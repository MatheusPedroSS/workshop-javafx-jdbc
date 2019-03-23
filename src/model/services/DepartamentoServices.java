package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartamentoDao;
import model.entities.Departamento;

public class DepartamentoServices {
	
	private DepartamentoDao dao = DaoFactory.criarDepartamentoDao();
	
	public List<Departamento> findAll(){
		return dao.buscarTodos();
	}
}
