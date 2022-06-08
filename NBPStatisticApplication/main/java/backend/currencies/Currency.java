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

}
