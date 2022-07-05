package frontend;

import backend.currencies.Currency;
import backend.interfaces.implementations.DataManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Tab2Controller {
	@FXML
	public ComboBox currency1;
	@FXML
	public ComboBox currency2;


	public Button showDataButton1;
	public Button showDataButton2;
	public LineChart<String, Double> currencyChart;
	List<Currency> cl;
	final DataManager dm = new DataManager();

	public void loadData() throws IOException, ParseException {
		cl = dm.getAllCurrencies();
		setCurrencyRates(cl);
	}

	private void setCurrencyRates(List<Currency> cl) {
		ArrayList<String> names = new ArrayList<>();
		for (Currency c : cl)
			names.add(c.getName());
		currency1.setItems(FXCollections.observableList(names));
		currency2.setItems(FXCollections.observableList(names));
		currency1.getSelectionModel().selectFirst();
		currency2.getSelectionModel().selectFirst();
	}

	public void showDataMonthly(MouseEvent mouseEvent) throws IOException, ParseException {
		showData(1);
	}

	private void showData(int monthsToSubtract) throws IOException, ParseException {
		String currentName1 = (String) currency1.getSelectionModel().getSelectedItem();
		String currentName2 = (String) currency2.getSelectionModel().getSelectedItem();
		Currency current1 = getSelectedCurrency(currentName1);
		Currency current2 = getSelectedCurrency(currentName2);

		String possibleDate = findLastPossibleDate();
		if (possibleDate.equals("")) {
			possibleDate = cl.get(0).getDate();
		}
		// Getting current date
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate now = LocalDate.parse(possibleDate, dtf);

		// Getting time frame
		LocalDate tempDate = LocalDate.parse(dtf.format(now));
		tempDate = tempDate.minusMonths(monthsToSubtract);
		String startDate = tempDate.toString();

		List<Currency> c1 = dm.getForPeriod(current1, startDate, dtf.format(now));
		List<Currency> c2 = dm.getForPeriod(current2, startDate, dtf.format(now));
		fillTheChart(c1,c2);
	}

	public void showDataQuarterly(MouseEvent mouseEvent) throws IOException, ParseException {
		showData(3);
	}

	public Currency getSelectedCurrency(String currentName){
		Currency current = null;
		for (Currency c : cl) {
			if (c.getName().equals(currentName)) {
				current = c;
				break;
			}
		}
		return current;
	}


	private void fillTheChart(List<Currency> c1, List<Currency> c2) {
		currencyChart.getData().clear();
		XYChart.Series<String, Double> series1 = new XYChart.Series<>();
		XYChart.Series<String, Double> series2 = new XYChart.Series<>();
		for (Currency curr : c1)
			series1.getData().add(new XYChart.Data<>(curr.getDate(), curr.getValueAsDouble()));
		for (Currency curr : c2)
			series2.getData().add(new XYChart.Data<>(curr.getDate(), curr.getValueAsDouble()));
		series1.setName(c1.get(0).getName());
		series2.setName(c2.get(0).getName());
		currencyChart.getData().addAll(series1, series2);
		currencyChart.getXAxis().setAnimated(false);
		currencyChart.getXAxis().lookup(".axis-label").setStyle("-fx-label-padding: -10 0 0 0;");
	}

	private String findLastPossibleDate() {
		for (Currency c : cl)
			if (c.getCode().equals("USD"))
				return c.getDate();
		return "";
	}

}
