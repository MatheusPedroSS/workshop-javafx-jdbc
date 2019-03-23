package gui.util;

import javafx.scene.control.TextField;

public class Limitacoes {
	public static void setTextFieldInteger(TextField txt) {
		txt.textProperty().addListener((observador, valorAntigo, valorNovo) -> {
			if(valorNovo != null && !valorNovo.matches("\\d*")) {
				txt.setText(valorAntigo);
			}
		});
	}
	public static void setTextFieldMaxLength(TextField txt, int maximo) {
		txt.textProperty().addListener((observador, valorAntigo, valorNovo) -> {
			if (valorNovo != null && valorNovo.length() > maximo) {
				txt.setText(valorAntigo);
			}
		});
	}
	public static void setTextFieldDouble(TextField txt) {
		txt.textProperty().addListener((observador, valorAntigo, valorNovo) -> {
			if (valorNovo != null && !valorNovo.matches("\\d*([\\.]\\d*)?")) {
				txt.setText(valorAntigo);
			}
		});
	}
}
