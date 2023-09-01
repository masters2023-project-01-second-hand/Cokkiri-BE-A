package com.cokkiri.secondhand.global.utill;

import java.text.DecimalFormat;

public class PriceFormatter {

	private static final DecimalFormat decFormat = new DecimalFormat("###,###");

	public static String addCommasTo(long price) {
		return decFormat.format(price);
	}
}
