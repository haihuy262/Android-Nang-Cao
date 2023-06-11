package huynhph30022.fpoly.assignmentmob201.newspaper;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import huynhph30022.fpoly.assignmentmob201.model.Newspaper;

public class TinTucLoader {
    private final ArrayList<Newspaper> list = new ArrayList<>();
    private Newspaper newspaper;
    private String textContent;

    public ArrayList<Newspaper> getTinTucList(InputStream inputStream) throws XmlPullParserException, IOException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(inputStream, null);
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String tagName = parser.getName();
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (tagName.equalsIgnoreCase("item")) {
                        newspaper = new Newspaper();
                    }
                    break;
                case XmlPullParser.TEXT:
                    textContent = parser.getText();
                    break;
                case XmlPullParser.END_TAG:
                    if (newspaper != null) {
                        if (tagName.equalsIgnoreCase("item"))
                            list.add(newspaper);
                        if (tagName.equalsIgnoreCase("title"))
                            newspaper.setTitle(textContent);
                        if (tagName.equalsIgnoreCase("description"))
                            newspaper.setDescription(textContent);
                        if (tagName.equalsIgnoreCase("link"))
                            newspaper.setLink(textContent);
                        if (tagName.equalsIgnoreCase("pubDate"))
                            newspaper.setPubDate(textContent);
                    }
                    break;
                default:
                    Log.e("nguyenhaihuy262", "eventType khác: " + eventType + ", tag = " + tagName);
                    break;
            }
            eventType = parser.next();
        }
        inputStream.close();
        return list;
    }
}
