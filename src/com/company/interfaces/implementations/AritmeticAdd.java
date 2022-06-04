package com.company.interfaces.implementations;

import com.company.interfaces.IAritmeticsAdd;

public class AritmeticAdd implements IAritmeticsAdd {

	@Override
	public Double Addition(double A, double B) {
		return A + B;
	}
}
