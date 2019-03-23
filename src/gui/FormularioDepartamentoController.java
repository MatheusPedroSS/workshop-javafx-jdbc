package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Limitacoes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FormularioDepartamentoController implements Initializable{

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
	
	@FXML
	public void onBtSalvarAction() {
		System.out.println("botão salvar");
	}
	
	@FXML
	public void onBtCancelarAction() {
		System.out.println("botão cancelar");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		Limitacoes.setTextFieldInteger(txtId);
		Limitacoes.setTextFieldMaxLength(txtName, 30);
	}
}
