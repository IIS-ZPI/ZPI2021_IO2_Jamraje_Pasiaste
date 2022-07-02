module com.example.projekt {
	requires javafx.controls;
	requires javafx.fxml;
	requires lombok;
	requires jcabi.http;
	requires javax.json.api;


	opens frontend to javafx.fxml;
	exports frontend;
}