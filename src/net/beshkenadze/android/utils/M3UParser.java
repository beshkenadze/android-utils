package net.beshkenadze.android.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import net.beshkenadze.android.utils.logger.MyLogger;

public class M3UParser {
	private ArrayList<M3UParser.Item> items = new ArrayList<M3UParser.Item>();
	String prefixName = "#EXTINF";

	public M3UParser(File f) {
		if (f.exists()) {
			String stream;
			try {
				stream = convertStreamToString(new FileInputStream(f));
				parseString(stream);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}
	}

	private void parseString(String stream) {
		if (stream == null)
			return;
		stream = stream.replaceAll("#EXTM3U", "").trim();
		String[] lines = stream.split("\n");
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i].trim();
			
			if (line.startsWith(prefixName)) {
				String[] nameInfo = line.split(",");
				String name = nameInfo[1].trim();
				String link = lines[i + 1];
				getItems().add(new Item(name, link));
			}
		}
	}

	public M3UParser(InputStream is) {
		parseString(convertStreamToString(is));
	}

	public M3UParser(String stream) {
		parseString(stream);
	}

	public String convertStreamToString(java.io.InputStream is) {
		try {
			return new java.util.Scanner(is).useDelimiter("\\A").next();
		} catch (java.util.NoSuchElementException e) {
			return "";
		}
	}

	public ArrayList<M3UParser.Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<M3UParser.Item> items) {
		this.items = items;
	}

	public class Item {
		private String name;
		private String link;

		public Item(String name, String link) {
			setName(name);
			setLink(link);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getLink() {
			return link;
		}

		public void setLink(String link) {
			this.link = link;
		}
	}
}
