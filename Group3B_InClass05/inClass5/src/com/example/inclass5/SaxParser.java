package com.example.inclass5;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Xml;

public class SaxParser {
	static public class FlickrSAXParser extends DefaultHandler {

		public ArrayList<String> getPhotosList() {
			return photosList;
		}

		ArrayList<String> photosList;

		static public ArrayList<String> ParseFlickrXML(InputStream in) throws IOException, SAXException {
			FlickrSAXParser parser = new FlickrSAXParser();
			Xml.parse(in, Xml.Encoding.UTF_8, parser);
			return parser.getPhotosList();
		}
		@Override
		public void startDocument() throws SAXException {
			photosList = new ArrayList<String>();
		}
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			if (localName.equals("photo")) {
				photosList.add(attributes.getValue("url_m"));
			}
		}
	}
}
