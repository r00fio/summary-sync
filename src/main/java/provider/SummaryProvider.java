package provider;

import entity.Summary;
import mapper.LocationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.SummaryParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by pixel on 3/28/15.
 */
public class SummaryProvider {
    private static final Logger log = LoggerFactory.getLogger(SummaryProvider.class);
    public static final String FIND_SUMMARIES_BASE_URL = "http://rabota.ua/employer/find/cv_list?";
    public static final String CV_BASE_URL = "http://rabota.ua/cv/";
    private static final String DESCRIPTION = "keyWords=";
    private static final String LOCATION = "&regionId=";
    private LocationMapper locationMapper;
    private SummaryParser summaryParser;

    public SummaryProvider(LocationMapper locationMapper, SummaryParser summaryParser) {
        this.locationMapper = locationMapper;
        this.summaryParser = summaryParser;
    }

    public Summary getSummary(String id) throws SummaryProviderException {
        String htmlDocument = fetchDocument(CV_BASE_URL + id);
        try {
            Summary summary = summaryParser.parseSummary(htmlDocument);
            summary.setId(id);
            return summary;
        } catch (IOException e) {
            log.error("Couldn't parse summary", e);
            throw new SummaryProviderException("Couldn't parse summary", e);
        }
    }

    public List<Summary> getSummariesLinks(String description, String location) throws SummaryProviderException {
        String locationId = locationMapper.map(location);
        if (locationId == null || locationId.isEmpty()) {
            throw new SummaryProviderException("Unknown location");
        }
        String url = new StringBuilder(FIND_SUMMARIES_BASE_URL)
                .append(DESCRIPTION).append(description)
                .append(LOCATION).append(locationId).toString();
        String htmlDocument = fetchDocument(url);
        try {
            return summaryParser.parseSummaryList(htmlDocument);
        } catch (IOException e) {
            log.error("Couldn't parse summaries list", e);
            throw new SummaryProviderException("Couldn't parse summaries list", e);
        }
    }

    private String fetchDocument(String link) {
        StringBuilder inputLine = new StringBuilder();

        try {
            URL url = new URL(link);
            URLConnection connection = url.openConnection();
            BufferedReader in =
                    new BufferedReader(new InputStreamReader(connection.getInputStream(),
                            Charset.forName("UTF-8").toString()));
            String line;
            while ((line = in.readLine()) != null) {
                inputLine.append(line);
            }
            in.close();
        } catch (IOException e) {
            throw new SummaryProviderException("Service unavailable", e);
        }
        return inputLine.toString();
    }

}
