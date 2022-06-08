package backend.interfaces.implementations;

import backend.interfaces.IAritmeticsAdd;

public class AritmeticAdd implements IAritmeticsAdd {

	@Override
	public Double Addition(double A, double B) {
		return A + B;
	}
}
