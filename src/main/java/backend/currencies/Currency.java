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

}
