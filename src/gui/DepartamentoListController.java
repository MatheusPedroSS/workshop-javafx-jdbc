package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Departamento;
import model.services.DepartamentoServices;

public class DepartamentoListController implements Initializable{
	
	private DepartamentoServices service;

	@FXML
	private TableView<Departamento> tableViewDepartamento;
	
	@FXML
	private TableColumn<Departamento, Integer> tableColunaId;
	
	@FXML
	private TableColumn<Departamento, String> tableColunaNome;
	
	@FXML
	private Button btNovo;
	
	private ObservableList<Departamento> obsList;
	
	@FXML
	public void onBtNovoAction() {
		System.out.println("Botão");
	}
	
	public void setDepartamentoService(DepartamentoServices service) {
		this.service = service;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}



	private void initializeNodes() {
		tableColunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColunaNome.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		Stage stage =(Stage) Main.getMainScene().getWindow();
		tableViewDepartamento.prefHeightProperty().bind(stage.heightProperty());
	}

	public void atualizarTableView() {
		if (service == null) {
			throw new IllegalStateException("Serviço null");
		}
		List<Departamento> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewDepartamento.setItems(obsList);
	}
}
