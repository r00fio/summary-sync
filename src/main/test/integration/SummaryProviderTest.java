package integration;

import org.junit.Test;
import provider.SummaryProvider;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class SummaryProviderTest {

    @Test
    public void testCheckConnection() throws Exception {
        URL url = new URL(SummaryProvider.FIND_SUMMARIES_BASE_URL);
        URLConnection connection = url.openConnection();
        InputStream inputStream = connection.getInputStream();
        assertNotNull(inputStream);
        assertTrue(inputStream.available() >0 );
    }
}