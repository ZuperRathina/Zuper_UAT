package UtilityPackages;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class SlackNotifier {

	public static void sendSlackMessage(String message) {
		try {

			String webhookUrl = "https://hooks.slack.com/services/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";

			URL url = new URL(webhookUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json");

            String payload = "{\"text\":\"" + message.replace("\\", "\\\\")
                                                     .replace("\"", "\\\"")
                                                     .replace("\n", "\\n")
                                                     .replace("\r", "")
                            + "\"}";

            //System.out.println("Payload: " + payload);

            OutputStream os = conn.getOutputStream();
            os.write(payload.getBytes("UTF-8"));
            os.flush();
            os.close();

            int responseCode = conn.getResponseCode();
            System.out.println("Slack Response Code: " + responseCode);

            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}