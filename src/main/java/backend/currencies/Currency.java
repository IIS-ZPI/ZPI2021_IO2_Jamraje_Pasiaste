package backend.currencies;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class Currency {
	private String table;
	private String name;
	private String code;
	private String value;
	private String date;
	private Double valueAsDouble;
	private Date dateAsDateObject;

	//Its not needed you can use "copy constructor through builder -> Object.toBuilder.build();
//	public Currency(Currency currency) {
//		table = currency.getTable();
//		name = currency.getName();
//		code = currency.getCode();
//		value = currency.getValue();
//		date = currency.getDate();
//	}
}
