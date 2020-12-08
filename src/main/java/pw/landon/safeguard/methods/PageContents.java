package pw.landon.safeguard.methods;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class PageContents {
    private PageContents() {}

    public static String read(String url) throws IOException {

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:71.0) Gecko/20100101 Firefox/71.0");
        connection.setRequestProperty("Host", "www.ipqualityscore.com");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setRequestProperty("Transfer-Encoding", "chunked");
        connection.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder string = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            string.append(line);
        }
        connection.getInputStream().close();
        connection.disconnect();
        return string.toString();

    }
}
