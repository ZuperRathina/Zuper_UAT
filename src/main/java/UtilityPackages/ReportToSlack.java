package UtilityPackages;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;

import BaseTest.Baseclass;

public class ReportToSlack extends Baseclass {
	private static String token = "xoxb-159793626610-10635849042503-wevTbsp8jNhltquTeMH567E5";
	private static String channelId = "C0AK06LKHBL";

	public static void uploadReport(String filePath, String environment, String account, long minutes, long seconds, String methodName) {

		try {

			File file = new File(filePath);

			if (!file.exists()) {
				System.out.println("Report file not found");
				return;
			}

			/* STEP 1 - Get Upload URL */

			URL url = new URL("https://slack.com/api/files.getUploadURLExternal");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			conn.setRequestProperty("Authorization", "Bearer " + token);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			String body = "filename=" + file.getName() + "&length=" + file.length();

			OutputStream os = conn.getOutputStream();
			os.write(body.getBytes());
			os.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			StringBuilder responseStr = new StringBuilder();

			while ((line = reader.readLine()) != null) {
				responseStr.append(line);
			}

			System.out.println("Step1 Response: " + responseStr.toString());

			JSONObject response1 = new JSONObject(responseStr.toString());

			reader.close();

			String uploadUrl = response1.getString("upload_url");
			String fileId = response1.getString("file_id");

			/* STEP 2 - Upload File */

			URL uploadURL = new URL(uploadUrl);

			HttpURLConnection uploadConn = (HttpURLConnection) uploadURL.openConnection();
			uploadConn.setDoOutput(true);
			uploadConn.setRequestMethod("POST");
			uploadConn.setRequestProperty("Content-Type", "application/octet-stream");

			FileInputStream fis = new FileInputStream(file);
			OutputStream out = uploadConn.getOutputStream();

			byte[] buffer = new byte[4096];
			int bytesRead;

			while ((bytesRead = fis.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
			int uploadResponseCode = uploadConn.getResponseCode();
			System.out.println("Upload Response Code: " + uploadResponseCode);

			fis.close();
			out.close();

			/* STEP 3 - Complete Upload + Share to Channel */

			URL completeUrl = new URL("https://slack.com/api/files.completeUploadExternal");

			HttpURLConnection completeConn = (HttpURLConnection) completeUrl.openConnection();

			completeConn.setRequestMethod("POST");
			completeConn.setDoOutput(true);

			completeConn.setRequestProperty("Authorization", "Bearer " + token);
			completeConn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDate today = LocalDate.now();
			String formattedDate = today.format(formatter);
			String comment;
			if (minutes > 0 || seconds > 0) {
			    comment = "*Zuper Connect – Sanity Test Execution Completed (Web)*\n\n"
			            + "*Date:* " + formattedDate + "\n"
			            + "*Environment:* " + environment + "\n"
			            + "*Account Used:* " + account + "\n"
			            + "*Execution Time:* " + minutes + "m " + seconds + "s\n\n"
			            + "For a detailed report, please download and view the attached file.";
			} else {
			    comment = "*Zuper Connect – Sanity Test Retried Scenario: "+methodName+"*\n\n"
			            + "*Date:* " + formattedDate + "\n"
			            + "*Environment:* " + environment + "\n"
			            + "*Account Used:* " + account + "\n\n"
			            + "For a detailed report, please download and view the attached file.";
			}

			String payload = "{" + "\"files\":[{\"id\":\"" + fileId + "\",\"title\":\"Sanity Test Report\"}],"
					+ "\"channel_id\":\"" + channelId + "\"," + "\"initial_comment\":\"" + comment.replace("\n", "\\n")
					+ "\"" + "}";

			OutputStream cos = completeConn.getOutputStream();
			cos.write(payload.getBytes("UTF-8"));
			cos.close();

			BufferedReader br = new BufferedReader(new InputStreamReader(completeConn.getInputStream()));

			String response = br.readLine();
			br.close();

			System.out.println("Slack Upload Response: " + response);

			System.out.println("Report successfully sent to Slack");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
