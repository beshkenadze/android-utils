package net.beshkenadze.android.utils;

import java.util.HashMap;

public class PhoneNumber {
	private String country = "7";
	private String prefix = null;
	private String number = null;

	public PhoneNumber() {

	}

	public PhoneNumber(HashMap<String, String> hashPhoneNumber) {
		if (hashPhoneNumber != null) {
			
			if (hashPhoneNumber.containsKey("country")) {
				setCountry(hashPhoneNumber.get("country"));
			}
				
			if (hashPhoneNumber.containsKey("prefix"))
				setPrefix(hashPhoneNumber.get("prefix"));
			if (hashPhoneNumber.containsKey("number"))
				setNumber(hashPhoneNumber.get("number"));
		}
	}

	public String getMSISDN() {
		return String.valueOf(getCountry()) + String.valueOf(getPrefix())
				+ String.valueOf(getNumber());
	}
	public String getCallNumber() {
		return "+"+getMSISDN();
	}
	public String getShortNumber() {
		return String.valueOf(getPrefix())
				+ String.valueOf(getNumber());
	}
	public long getFullNumberLong() {
		return PhoneUtil.convertToLong(String.valueOf(getCountry())
				+ String.valueOf(getPrefix()) + String.valueOf(getNumber()));
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public boolean isValid() {
		return (getPrefix() != null && getNumber() != null);
	}
}
