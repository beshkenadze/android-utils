package net.beshkenadze.android.utils;

public class Text {
	/**
	 * returns the string, the first char lowercase
	 * 
	 * @param target
	 * @return
	 */
	public final static String asLowerCaseFirstChar(final String target) {

		if ((target == null) || (target.length() == 0)) {
			return target; // You could omit this check and simply live with an
							// exception if you like
		}
		return Character.toLowerCase(target.charAt(0))
				+ (target.length() > 1 ? target.substring(1) : "");
	}

	/**
	 * returns the string, the first char uppercase
	 * 
	 * @param target
	 * @return
	 */
	public final static String asUpperCaseFirstChar(final String target) {

		if ((target == null) || (target.length() == 0)) {
			return target; // You could omit this check and simply live with an
							// exception if you like
		}
		return Character.toUpperCase(target.charAt(0))
				+ (target.length() > 1 ? target.substring(1) : "");
	}

	/**
	 * returns the string, the first char uppercase
	 * 
	 * @param target
	 * @return
	 */
	public final static String asUpperEveryCaseFirstChar(String target) {
		String[] stringCatches = { " ", "-", " of ", " of the ", "'", "'s " };
		String[] stringParts = null;
		String stringFinish = "";
		String stringCatch = "";

		for (String string : stringCatches) {
			if (target.contains(string)) {
				stringParts = target.split(string);
				stringCatch = string;
			}
		}

		if (stringParts != null) {
			for (int i = 0; i < stringParts.length; i++) {
				stringFinish += Text.asUpperCaseFirstChar(stringParts[i]);
				if(stringParts.length > i+1) {
					stringFinish += stringCatch;
				}
			}
		}

		if (stringFinish.length() > 0) {
			return stringFinish;
		}
		return Text.asUpperCaseFirstChar(target);
	}
}
