package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

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
		System.out.println("OnMenuItemDepartamentoAction");
	}
	@FXML
	public void OnMenuItemSobreAction() {
		System.out.println("OnMenuItemSobreAction");
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}

}
