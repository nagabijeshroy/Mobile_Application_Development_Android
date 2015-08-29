package com.example.inclass5;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class PullParser {
	static ArrayList<String> parsePersons(InputStream in) throws XmlPullParserException, IOException {
		XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
		parser.setInput(in, "UTF-8");
		ArrayList<String> urlList = new ArrayList<String>();
		int event = parser.getEventType();
		while (event != XmlPullParser.END_DOCUMENT) {
			switch (event) {
			case XmlPullParser.START_TAG:
				if (parser.getName().equals("photo")) {
					urlList.add(parser.getAttributeValue(null, "url_m").trim());
				}
				break;
			default:
				break;
			}
			event = parser.next();
		}
		return urlList;
	}

}
