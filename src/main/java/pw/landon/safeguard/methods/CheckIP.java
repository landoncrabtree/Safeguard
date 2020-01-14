package pw.landon.safeguard.methods;

import pw.landon.safeguard.SafeguardPlugin;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;

public class CheckIP {
    private CheckIP() {}

    public static String API_URL_PREFIX = "https://www.ipqualityscore.com/api/json/ip/";
    public static String API_URL_ARGS = "?strictness=0&allow_public_access_points=true&fast=true&lighter_penalties=true&mobile=true";
    public static String API_KEY = SafeguardPlugin.getInstance().getConfig().getString("api_key");

    public static boolean isUsingProxy(String ip) throws IOException {

        String url = API_URL_PREFIX + API_KEY + "/" + ip + API_URL_ARGS;
        String urlResponse = PageContents.read(url);
        return (urlResponse.contains("\"proxy\":true"));

    }

    public static boolean isUsingVPN(String ip) throws IOException {

        String url = API_URL_PREFIX + API_KEY + "/" + ip + API_URL_ARGS;
        String urlResponse = PageContents.read(url);
        return (urlResponse.contains("\"vpn\":true"));

    }

    public static boolean hasHighFraudScore(String ip) throws IOException {

        String url = API_URL_PREFIX + API_KEY + "/" + ip + API_URL_ARGS;
        String urlResponse = PageContents.read(url);
        JsonObject jsonResponse = new JsonParser().parse(urlResponse).getAsJsonObject();
        int fraud_score_config = SafeguardPlugin.getInstance().getConfig().getInt("options.allow_fraud_score_less_than");
        int fraud_score = Integer.parseInt(jsonResponse.get("fraud_score").toString());
        System.out.println(fraud_score);
        return (fraud_score >= fraud_score_config);

    }
}

