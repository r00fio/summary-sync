package mapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import provider.SummaryProvider;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pixel on 3/28/15.
 */
public class LocationMapper {

    private final Map<String, String> locationToId = new HashMap<>();

    /**
     * Parses locationToId from rabota.ua
     * @throws IOException
     */
    public void init() throws IOException {
        URL url = new URL(new StringBuilder(SummaryProvider.FIND_SUMMARIES_BASE_URL).toString());
        URLConnection connection = url.openConnection();
        InputStream inputStream = connection.getInputStream();

        Document doc = Jsoup.parse(inputStream, Charset.defaultCharset().toString(), SummaryProvider.FIND_SUMMARIES_BASE_URL);
        Element cityIds = doc.getElementById("centerZone_Main_CityPicker_options");
        Elements inputElements = cityIds.getElementsByTag("li");

        inputElements.forEach((element) -> {
            locationToId.put(element.textNodes().get(0).text(), element.attr("data-id"));
        });
    }

    public String map(String location) {
        return locationToId.get(location);
    }
}
