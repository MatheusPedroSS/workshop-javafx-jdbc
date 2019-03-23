package gui;

import java.net.URL;
import java.util.ResourceBundle;

import db.DbException;
import gui.util.Alerts;
import gui.util.Limitacoes;
import gui.util.Utilidades;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.entities.Departamento;
import model.services.DepartamentoServices;

public class FormularioDepartamentoController implements Initializable{

	private Departamento entity;
	
	private DepartamentoServices service;
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private Label labelErrorNome;
	
	@FXML
	private Button btSalvar;
	
	@FXML
	private Button btCancelar;
	
	public void setDepartamento(Departamento entity) {
		this.entity = entity;
	}
	
	public void setDepartmentoService(DepartamentoServices service) {
		this.service = service;
	}
	
	@FXML
	public void onBtSalvarAction(ActionEvent event) {
		if(entity == null) {
			throw new IllegalStateException("Entity null");
		}
		if (service == null) {
			throw new IllegalStateException("Service null");
		}
		try {
			entity = getDadosFormulario();
			service.saveOrUpdate(entity);
			Utilidades.stageAtual(event).close();
		}
		catch (DbException e) {
			Alerts.mostrarAlerta("Erro ao salvar objeto", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private Departamento getDadosFormulario() {
		Departamento obj = new Departamento();
		
		obj.setId(Utilidades.tryParseToInt(txtId.getText()));
		obj.setName(txtName.getText());
		
		return obj;
	}

	@FXML
	public void onBtCancelarAction(ActionEvent event) {
		Utilidades.stageAtual(event).close();
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		Limitacoes.setTextFieldInteger(txtId);
		Limitacoes.setTextFieldMaxLength(txtName, 30);
	}
	
	public void atualizarDadosFormulario() {
		if(entity == null) {
			throw new IllegalStateException("Entity null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
	}
}
