package frontend;

import backend.currencies.Currency;
import backend.interfaces.implementations.DataManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tab1Controller {
	@FXML
	public ComboBox currencyList;
	public ComboBox period;
	public Label mediana;
	public Label dominanta;
	public Label odchylenie;
	public Label zmiennosci;
	public Button showDataButton;
	public LineChart<String, Double> currencyChart;

	final DataManager dm = new DataManager();
	List<Currency> cl;

	public void loadData() throws IOException, ParseException {
		cl = dm.getAllCurrencies();
		setCurrencyRates(cl);
		setPeriods();
	}

	private void setPeriods() {
		ArrayList<String> p = new ArrayList<>();
		p.add("Tydzień");
		p.add("2 tygodnie");
		p.add("Miesiąc");
		p.add("Kwartał");
		p.add("6 miesięcy");
		p.add("Rok");
		period.setItems(FXCollections.observableList(p));
		period.getSelectionModel().selectFirst();
	}

	private void setCurrencyRates(List<Currency> cl) {
		ArrayList<String> names = new ArrayList<>();
		for (Currency c : cl)
			names.add(c.getName());
		currencyList.setItems(FXCollections.observableList(names));
		currencyList.getSelectionModel().selectFirst();
	}

	public void showData(MouseEvent mouseEvent) throws IOException, ParseException {
		String currentName = (String) currencyList.getSelectionModel().getSelectedItem();
		Currency current = null;
		for (Currency c : cl) {
			if (c.getName().equals(currentName)) {
				current = c;
				break;
			}
		}

		// Getting current date
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();

		// Getting time frame
		String timeFrame = (String) period.getSelectionModel().getSelectedItem();
		String startDate;
		LocalDate tempDate = LocalDate.parse(dtf.format(now));
		switch (timeFrame) {
			case "Tydzień":
				tempDate = tempDate.minusDays(7);
				break;
			case "2 tygodnie":
				tempDate = tempDate.minusWeeks(2);
				break;
			case "Miesiąc":
				tempDate = tempDate.minusMonths(1);
				break;
			case "Kwartał":
				tempDate = tempDate.minusMonths(3);
				break;
			case "6 miesięcy":
				tempDate = tempDate.minusMonths(6);
				break;
			case "Rok":
				tempDate = tempDate.minusYears(1);
				break;
		}
		startDate = tempDate.toString();

		List<Currency> c = dm.getForPeriod(current, startDate, dtf.format(now));
		setMediana(c);
		setDominanta(c);
		setOdchylenie(c);
		setZmiennosci(c);
		fillTheChart(c);
	}

	private void fillTheChart(List<Currency> c) {
		currencyChart.getData().clear();
		XYChart.Series<String, Double> series = new XYChart.Series<>();
		for (Currency curr : c)
			series.getData().add(new XYChart.Data<>(curr.getDate(), curr.getValueAsDouble()));
		series.setName(c.get(0).getName());
		currencyChart.getData().addAll(series);
		currencyChart.getXAxis().setAnimated(false);
		currencyChart.getXAxis().lookup(".axis-label").setStyle("-fx-label-padding: -10 0 0 0;");
	}

	private void setZmiennosci(List<Currency> c) {
		try {
			double sum = 0.0;
			for (Currency curr : c)
				sum += curr.getValueAsDouble();
			zmiennosci.setText(String.valueOf(Double.parseDouble(odchylenie.getText()) / (sum / c.size())));
		} catch (Exception e) {
			System.out.println("Something is wrong with data");
			zmiennosci.setText("Corrupted data");
		}
	}

	private void setOdchylenie(List<Currency> c) {
		try {
			ArrayList<Double> sDList = new ArrayList<>();
			for (Currency curr : c)
				sDList.add(curr.getValueAsDouble());
			double sum = 0.0, standard_deviation = 0.0;
			int array_length = sDList.size();
			for (double temp : sDList) {
				sum += temp;
			}
			double mean = sum / array_length;
			for (double temp : sDList) {
				standard_deviation += Math.pow(temp - mean, 2);
			}
			odchylenie.setText(String.valueOf(Math.sqrt(standard_deviation / array_length)));
		} catch (Exception e) {
			System.out.println("Something is wrong with data");
			odchylenie.setText("Corrupted data");
		}
	}

	private void setDominanta(List<Currency> c) {
		try {
			ArrayList<Double> modeList = new ArrayList<>();
			for (Currency curr : c)
				modeList.add(curr.getValueAsDouble());
			int maxCount = 0;
			Double maxValue = 0.0;
			for (int i = 0; i < modeList.size(); i++) {
				int count = 0;
				for (Double aDouble : modeList) {
					if (aDouble.equals(modeList.get(i)))
						++count;
				}
				if (count > maxCount) {
					maxCount = count;
					maxValue = modeList.get(i);
				}
			}
			dominanta.setText(String.valueOf(maxValue));
		} catch (Exception e) {
			System.out.println("Something is wrong with data");
			dominanta.setText("Corrupted data");
		}

	}

	private void setMediana(List<Currency> c) {
		ArrayList<Double> medianList = new ArrayList<>();
		for (Currency curr : c)
			medianList.add(curr.getValueAsDouble());
		Collections.sort(medianList);
		try {
			Double middle = (medianList.get(medianList.size() / 2) + medianList.get(medianList.size() / 2 - 1)) / 2;
			mediana.setText(String.valueOf(middle));
		} catch (Exception e) {
			System.out.println("Something is wrong with data");
			mediana.setText("Corrupted data");
		}

	}
}
