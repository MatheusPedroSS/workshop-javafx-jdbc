package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import model.services.DepartamentoServices;

public class MainViewController implements Initializable{
	
	@FXML
	private MenuItem menuItemVendedor;
	
	@FXML
	private MenuItem menuItemDepartamento;
	
	@FXML
	private MenuItem MenuItemSobre;
	
	@FXML
	public void OnMenuItemVendedorAction() {
		System.out.println("OnMenuItemVendedorAction");
	}
	@FXML
	public void OnMenuItemDepartamentoAction() {
		loadView2("/gui/DepartamentoList.fxml");
	}
	@FXML
	public void OnMenuItemSobreAction() {
		loadView("/gui/Sobre.fxml");
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}

	private synchronized void loadView(String absoluteName) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
		}
		catch (IOException e) {
			Alerts.mostrarAlerta("IO Exception", "Erro ao carregar a view", e.getMessage(), AlertType.ERROR);
		}
	}
	
	private synchronized void loadView2(String absoluteName) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			DepartamentoListController controller = loader.getController();
			controller.setDepartamentoService(new DepartamentoServices());
			controller.atualizarTableView();
		}
		catch (IOException e) {
			Alerts.mostrarAlerta("IO Exception", "Erro ao carregar a view", e.getMessage(), AlertType.ERROR);
		}
	}
}
