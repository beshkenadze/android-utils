package com.odesanmi.m3ufileparser;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class M3U_Parser {

	public M3U_Parser() throws Exception {

	}

	public String convertStreamToString(java.io.InputStream is) {
		try {
			return new java.util.Scanner(is).useDelimiter("\\A").next();
		} catch (java.util.NoSuchElementException e) {
			return "";
		}
	}

	public M3UHolder parseFile(File f) throws FileNotFoundException {
		if (f.exists()) {
			String stream = convertStreamToString(new FileInputStream(f));
			stream = stream.replaceAll("#EXTM3U", "").trim();
			String[] arr = stream.split("#EXTINF.*,");
			String urls = "", data = "";
			// clean
			{
				for (int n = 0; n < arr.length; n++) {
					if (arr[n].contains("http")) {
						String nu = arr[n].substring(arr[n].indexOf("http://"),
								arr[n].indexOf(".mp3") + 4);

						urls = urls.concat(nu);
						data = data.concat(arr[n].replaceAll(nu, "").trim())
								.concat("&&&&");
						urls = urls.concat("####");
					}
				}
			}
			return new M3UHolder(data.split("&&&&"), urls.split("####"));
		}
		return null;
	}

	public class M3UHolder {
		private String[] data, url;

		public M3UHolder(String[] names, String[] urls) {
			this.data = names;
			this.url = urls;
		}

		int getSize() {
			if (url != null)
				return url.length;
			return 0;
		}

		String getName(int n) {
			return data[n];
		}

		String getUrl(int n) {
			return url[n];
		}
	}
}
