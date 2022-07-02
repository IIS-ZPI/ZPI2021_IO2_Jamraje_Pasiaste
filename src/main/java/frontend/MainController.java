package frontend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;

public class MainController implements Initializable {
	@FXML
	Tab first;
	@FXML
	Tab second;

	Tab1Controller tab1controller;
	Tab2Controller tab2controller;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("tab 1.fxml"));
		try
		{
			AnchorPane anch1 = loader.load();
			first.setContent(anch1);
			tab1controller = loader.getController();
			tab1controller.loadData();
		}
		catch(IOException iex)
		{
			System.out.println("File not found");
		} catch (ParseException e) {
			System.out.println("Couldn't parse data!");
		}
		loader = new FXMLLoader(getClass().getResource("tab 2.fxml"));
		try
		{
			AnchorPane anch2 = loader.load();
			second.setContent(anch2);
			tab2controller = loader.getController();
			tab2controller.loadData();
		}
		catch(IOException iex)
		{
			System.out.println("File not found");
		} catch (ParseException e) {
			System.out.println("Couldn't parse data!");
		}
	}
}