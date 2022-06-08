package backend.currencies;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.*;

@Data
@AllArgsConstructor
public class Currency {
	private String table;
	private String name;
	private String code;
	private String value;
	private String date;

	public Currency(Currency currency) {
		table = currency.getTable();
		name = currency.getName();
		code = currency.getCode();
		value = currency.getValue();
		date = currency.getDate();
	}
}
