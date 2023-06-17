package huynhph30022.fpoly.dethi1706.rss;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import huynhph30022.fpoly.dethi1706.model.Feel;

public class TinTucLoaderPH30022 {
    ArrayList<Feel> list = new ArrayList<>();
    Feel feel;
    String textContent;

    public ArrayList<Feel> getTinTucListPH30022(InputStream inputStream) throws XmlPullParserException, IOException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(inputStream, null);
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String tagName = parser.getName();
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (tagName.equalsIgnoreCase("item")) {
                        feel = new Feel();
                    }
                    break;
                case XmlPullParser.TEXT:
                    textContent = parser.getText();
                    break;
                case XmlPullParser.END_TAG:
                    if (feel != null) {
                        if (tagName.equalsIgnoreCase("item"))
                            list.add(feel);
                        if (tagName.equalsIgnoreCase("title"))
                            feel.setTitle(textContent);
                        if (tagName.equalsIgnoreCase("description"))
                            feel.setDescription(textContent);
                        if (tagName.equalsIgnoreCase("link"))
                            feel.setLink(textContent);
                        if (tagName.equalsIgnoreCase("pubDate"))
                            feel.setPubDate(textContent);
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
