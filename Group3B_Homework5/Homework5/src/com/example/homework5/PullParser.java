/*
 * Naga Bijesh Roy Raya
 * Shyam Mohan Raman
 */

package com.example.homework5;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

public class PullParser {
	static ArrayList<Application> parseApplications(InputStream in) throws XmlPullParserException, IOException {
		XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
		parser.setInput(in, "UTF-8");
		ArrayList<Application> applicationList = new ArrayList<Application>();
		Application application = null;
		int event = parser.getEventType();
		while (event != XmlPullParser.END_DOCUMENT) {
			switch (event) {
			case XmlPullParser.START_TAG:
				if (parser.getName().equals("entry")) {
					application = new Application();
				} else if (application != null && parser.getName().equals("id")) {
					application.setId(Integer.parseInt(parser.getAttributeValue(null, "im:id")));
				} else if (application != null && parser.getName().equals("title")) {
					if (application != null) {
						application.setAppTitle(parser.nextText());
					}
				} else if (application != null && parser.getName().equals("link")) {
					Log.d("url", parser.getAttributeValue(null, "href"));
					application.setUrl(parser.getAttributeValue(null, "href"));
				} else if (application != null && parser.getName().equals("im:artist")) {
					application.setDeveloperName(parser.nextText());
				} else if (application != null && parser.getName().equals("im:price")) {
					application.setPrice(Double.parseDouble(parser.getAttributeValue(null, "amount")));
				} else if (application != null && parser.getName().equals("im:image")) {
					application.setLargePhotoUrl(parser.nextText());
				}
				break;
			case XmlPullParser.END_TAG:
				if (parser.getName().equals("entry")) {
					applicationList.add(application);
					application = null;
				}
				break;
			}
			event = parser.next();
		}
		return applicationList;
	}
}
