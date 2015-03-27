package com.spitspce.space;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class EventsXmlParser {

    private static final String ns = null;

    public List parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List events = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, "events");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("event")) {
                events.add(readEvent(parser));
            } else {
                skip(parser);
            }
        }
        return events;
    }

    public static class Event {
        public final int id;
        public final String name;
        public final String introduction;
        public final String contact;
        public final String email;
        public final String category;
        public final String prizes;
        public final String rules;
        public final String venue;

        public Event(int id, String name, String introduction, String contact, String email, String category,String venue,String prizes, String rules) {
            this.id = id;
            this.name = name;
            this.introduction = introduction;
            this.contact = contact;
            this.email = email;
            this.category = category;
            this.venue=venue;
            this.prizes = prizes;
            this.rules = rules;
        }
    }

    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
// to their respective "read" methods for processing. Otherwise, skips the tag.
    private Event readEvent(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "event");
        int id = -1;
        String name = null;
        String introduction = null;
        String contact = null;
        String email = null;
        String category = null;
        String prizes = null;
        String rules = null;
        String venue = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String tagName = parser.getName();
            switch (tagName) {
                case "event_id":
                    id = Integer.parseInt(readTextField(parser, tagName));
                    break;
                case "event_name":
                    name = readTextField(parser, tagName);
                    break;
                case "introduction":
                    introduction = readTextField(parser, tagName);
                    break;
                case "contact":
                    contact = readTextField(parser, tagName);
                    break;
                case "email":
                    email = readTextField(parser, tagName);
                    break;
                case "category":
                    category = readTextField(parser, tagName);
                    break;
                case "venue":
                    venue = readTextField(parser, tagName);
                    break;
                case "prizes":
                    prizes = readTextField(parser, tagName);
                    break;
                case "rules":
                    rules = readTextField(parser, tagName);
                    break;
                default:
                    skip(parser);
                    break;
            }


        }
        return new Event(id, name, introduction, contact, email, category, venue, prizes, rules);
    }

    private String readTextField(XmlPullParser parser, String fieldName) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, fieldName);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, fieldName);
        return title;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
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
