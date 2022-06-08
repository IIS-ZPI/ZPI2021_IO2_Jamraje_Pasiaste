package backend.interfaces.implementations;

import backend.interfaces.Division;

public class ArithmeticsDiv implements Division {

	@Override
	public Number divide(Number a, Number b) {
		return a.doubleValue() / b.doubleValue();
	}
}
