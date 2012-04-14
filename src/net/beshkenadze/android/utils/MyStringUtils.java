package net.beshkenadze.android.utils;

import java.util.ArrayList;

public class MyStringUtils {
	/**
	 * Method to join array elements of type string
	 * 
	 * @author Hendrik Will, imwill.com
	 * @param inputArray
	 *            Array which contains strings
	 * @param glueString
	 *            String between each array element
	 * @return String containing all array elements seperated by glue string
	 */
	public static String implodeArray(String[] inputArray, String glueString) {

		/** Output variable */
		String output = "";

		if (inputArray.length > 0) {
			StringBuilder sb = new StringBuilder();
			sb.append(inputArray[0]);

			for (int i = 1; i < inputArray.length; i++) {
				sb.append(glueString);
				sb.append(inputArray[i]);
			}

			output = sb.toString();
		}

		return output;
	}

	/**
	 * Method to join array elements of type string
	 * 
	 * @author Aleksandr Beshkenadze
	 * @param inputArray
	 *            ArrayList which contains strings
	 * @param glueString
	 *            String between each array element
	 * @return String containing all array elements seperated by glue string
	 */
	public static String implodeArray(ArrayList<String> inputArray,
			String glueString) {

		/** Output variable */
		String output = "";

		if (inputArray.size() > 0) {
			StringBuilder sb = new StringBuilder();
			sb.append(inputArray.get(0));

			for (int i = 1; i < inputArray.size(); i++) {
				sb.append(glueString);
				sb.append(inputArray.get(i));
			}

			output = sb.toString();
		}

		return output;
	}
}
