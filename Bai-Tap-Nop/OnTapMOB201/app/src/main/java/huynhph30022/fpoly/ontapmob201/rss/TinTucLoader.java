package huynhph30022.fpoly.ontapmob201.rss;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import huynhph30022.fpoly.ontapmob201.model.Feel;

public class TinTucLoader {
    ArrayList<Feel> list = new ArrayList<>();
    String textContent;
    Feel objFeel = new Feel();
    String TAG = "TinTucLoader";

    public ArrayList<Feel> getTinTucList(InputStream inputStream) throws XmlPullParserException, IOException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(inputStream, null);
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String tagName = parser.getName();
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (tagName.equalsIgnoreCase("item")) {
                        objFeel = new Feel();
                    }
                    break;
                case XmlPullParser.TEXT:
                    textContent = parser.getText();
                    break;
                case XmlPullParser.END_TAG:
                    if (objFeel != null) {
                        if (tagName.equalsIgnoreCase("item"))
                            list.add(objFeel);
                        if (tagName.equalsIgnoreCase("title"))
                            objFeel.setTitle(textContent);
                        if (tagName.equalsIgnoreCase("description"))
                            objFeel.setDescription(textContent);
                        if (tagName.equalsIgnoreCase("link"))
                            objFeel.setLink(textContent);
                        if (tagName.equalsIgnoreCase("pubDate"))
                            objFeel.setPubDate(textContent);
                    }
                    break;
                default:
                    Log.d(TAG, "eventType khác: " + eventType + ", tag = " + tagName);
                    break;
            }
            eventType = parser.next();
        }
        inputStream.close();
        return list;
    }
}
