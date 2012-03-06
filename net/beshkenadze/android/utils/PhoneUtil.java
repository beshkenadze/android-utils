package net.beshkenadze.android.utils;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneUtil {
	static public HashMap<String, String> parsePhoneNumber(String phoneNumber) {
		return parsePhoneNumber(phoneNumber, "7");
	}
	public static HashMap<String, String> parsePhoneNumber(
			String phoneNumber, String simCountryIso) {
		if(phoneNumber == null || simCountryIso == null)
			return null;
		Pattern pattern = Pattern
				.compile("^\\+?(\\d(?=\\d{10}))?(\\d{3}?)?([\\d]{7,10})$");
		Matcher matcher = pattern.matcher(phoneNumber);
		HashMap<String, String> data = null;
		if (matcher.matches()) {
			if (matcher.groupCount() == 3) {
				String country = matcher.group(1);
				String prefix = matcher.group(2);
				String number = matcher.group(3);
				data = new HashMap<String, String>();
				if (country != null)
					data.put("country", removeSymbolsFromNubmer(country));
				else 
					data.put("country", simCountryIso);
				if (prefix != null)
					data.put("prefix", removeSymbolsFromNubmer(prefix));
				if (number != null)
					data.put("number", removeSymbolsFromNubmer(number));
			}
		}
		return data;
	}
	public static boolean isValid(HashMap<String, String> phoneData) {
		return (phoneData != null && phoneData.containsKey("prefix")
				&& phoneData.containsKey("number")
				&& phoneData.get("prefix") != null
				&& phoneData.get("number") != null && convertToLong(phoneData) > 0);
	}
	private static String removeSymbolsFromNubmer(String phoneNumber) {
		return phoneNumber.replace("-", "").replace(" ", "").replace("(", "").replace(")", "").trim();
	}
	private static long convertToLong(HashMap<String, String> phoneData) {
		try {
			return Long.parseLong(phoneData.get("country") + phoneData.get("prefix")
					+ phoneData.get("number"));
		} catch (Exception e) {
			Debug.e(e.toString());
		}
		return 0;
	}
	public static long convertToLong(String phoneItemData) {
		try {
			return Long.parseLong(phoneItemData);
		} catch (Exception e) {
			Debug.e(e.toString());
		}
		return 0;
	}
	
}
