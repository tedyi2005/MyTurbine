package com.example.teddykidanne.myturbine;

/**
 * Created by Teddykidanne on 7/22/16.
 */
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductXmlParser {

    private static final String ns = null;
    String productName, categoryName, productDescription, productThumbnail, averageRatingImageURL, numberOfRatings, rating;


    /**
     * This is the only function need to be called from outside the class
     */
    public List<HashMap<String, String>> parse(Reader reader)
            throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(reader);
            parser.nextTag();
            return readProducts(parser);
        } finally {

        }
    }

    /**
     * This method read each country in the xml data and add it to List
     */
    private List<HashMap<String, String>> readProducts(XmlPullParser parser)
            throws XmlPullParserException, IOException {

        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        parser.require(XmlPullParser.START_TAG, ns, "ads");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();
            if (name.equals("ad")) {
                list.add(readProduct(parser));
            } else {
                skip(parser);
            }
        }
        return list;
    }

    /**
     * This method read a country and returns its corresponding HashMap construct
     */
    private HashMap<String, String> readProduct(XmlPullParser parser)
            throws XmlPullParserException, IOException {

        parser.require(XmlPullParser.START_TAG, ns, "ad");
//
//        String countryName = parser.getAttributeValue(ns, "name");
//        String flag = parser.getAttributeValue(ns, "flag");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();


            if (name.equals("productName")) {
                productName = readProductName(parser);
            } else if (name.equals("categoryName")) {
                categoryName = readCategoryName(parser);
            } else if (name.equals("productDescription")) {
                productDescription = readProductDescription(parser);
            } else if (name.equals("productThumbnail")) {
                productThumbnail = readProductThumbnail(parser);
            } else if (name.equals("averageRatingImageURL")) {
                averageRatingImageURL = readAverageRatingImageURL(parser);
            } else if (name.equals("numberOfRatings")) {
                numberOfRatings = readNumberOfRatings(parser);
            } else if (name.equals("rating")) {
                rating = readRating(parser);
            } else {
                skip(parser);
            }
        }

//        String details = "Language : " + language + "\n" +
//                "Capital : " + capital + "\n" +
//                "Currency : " + currency + "(" + currencyCode + ")";
//

        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("productName", productName);
        hm.put("categoryName", categoryName);
        hm.put("productDescription", productDescription);
        hm.put("productThumbnail", productThumbnail);
        hm.put("productDescription", productDescription);
        hm.put("averageRatingImageURL", averageRatingImageURL);
        hm.put("numberOfRatings", numberOfRatings);
        hm.put("rating", rating);


        return hm;
    }

    /**
     * Process language tag in the xml data
     */
    private String readProductName(XmlPullParser parser)
            throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "productName");
        String language = readText(parser);
        return language;
    }

    private String readCategoryName(XmlPullParser parser)
            throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "categoryName");
        String currency = readText(parser);
        return currency;
    }

    private String readProductDescription(XmlPullParser parser)
            throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "productDescription");
        String currency = readText(parser);
        return currency;
    }

    private String readProductThumbnail(XmlPullParser parser)
            throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "productThumbnail");
        String currency = readText(parser);
        return currency;
    }

    private String readAverageRatingImageURL(XmlPullParser parser)
            throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "averageRatingImageURL");
        String currency = readText(parser);
        return currency;
    }

    private String readNumberOfRatings(XmlPullParser parser)
            throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "numberOfRatings");
        String currency = readText(parser);
        return currency;
    }

    private String readRating(XmlPullParser parser)
            throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "rating");
        String currency = readText(parser);
        return currency;
    }

    /**
     * Getting Text from an element
     */
    private String readText(XmlPullParser parser)
            throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}