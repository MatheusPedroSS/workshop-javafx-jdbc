package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Limitacoes;
import gui.util.Utilidades;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Departamento;
import model.exceptions.ValidationException;
import model.services.DepartamentoServices;

public class FormularioDepartamentoController implements Initializable{

	private Departamento entity;
	
	private DepartamentoServices service;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
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
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
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
			notifyDataChangeListeners();
			Utilidades.stageAtual(event).close();
		}
		catch (ValidationException e) {
			setErrorMessages(e.getErros());
		}
		catch (DbException e) {
			Alerts.mostrarAlerta("Erro ao salvar objeto", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notifyDataChangeListeners() {
		for(DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}

	private Departamento getDadosFormulario() {
		Departamento obj = new Departamento();
		
		ValidationException exception = new ValidationException("Erro de validação");
		
		obj.setId(Utilidades.tryParseToInt(txtId.getText()));
		
		if(txtName.getText() == null || txtName.getText().trim().equals("")) {
			exception.addError("name", "O campo não pode ser vazio");
		}
		
		obj.setName(txtName.getText());
		
		if(exception.getErros().size() > 0) {
			throw exception;
		}
		
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
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		
		if(fields.contains("name")) {
			labelErrorNome.setText(errors.get("name"));
		}
		
	}
}
