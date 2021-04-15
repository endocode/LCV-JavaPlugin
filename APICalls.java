import org.postgresql.util.PGobject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class APICalls {
    private static HttpURLConnection con;
    private static int sendGetRequest(String url) throws IOException, InterruptedException {
      var httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
      var request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
      var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
      return response.statusCode();
    }
    public static void GetRequest(String url) throws IOException, InterruptedException {
        System.out.println("Connecting to LCV ... ");
        try {
        int status = sendGetRequest(url);
        if (status == 200) {
            System.out.println(url+" is reachable.");
        } else if (status != 404) {
            System.out.println("APIEndpoints is not responding");
        }
      } catch (IOException | InterruptedException e) {
        System.out.println(e);  }
    }
    public static PGobject LCVPostRequest(String InboundLicensesList) throws IOException {
      // this string should be retrieved somehow.
      String OutboundLicense = "MIT";
      var url = "http://0.0.0.0:8080/LicensesInputSPDX?InboundLicenses="+InboundLicensesList+"&OutboundLicense="+OutboundLicense+"";
      var urlParameters = "";
      byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
      try {
          var myurl = new URL(url);
          con = (HttpURLConnection) myurl.openConnection();
          con.setDoOutput(true);
          con.setRequestMethod("POST");
          con.setRequestProperty("User-Agent", "Java client");
          con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
          con.setRequestProperty("charset", "utf-8");

          try (var wr = new DataOutputStream(con.getOutputStream())) {
              wr.write(postData);
          }
          StringBuilder content;
          try (var br = new BufferedReader(
              new InputStreamReader(con.getInputStream()))) {
                  String line;
                  content = new StringBuilder();
                  while ((line = br.readLine()) != null) {
                      content.append(line);
                      content.append(System.lineSeparator());
              }
          }
          // System.out.println(content.toString());
          // JSONObject contentJsonFormat = new JSONObject(content);
          PGobject jsonContent = new PGobject();
          jsonContent.setType("json");
          jsonContent.setValue(String.valueOf(content));

          //System.out.println(jsonContent);
          return jsonContent;
      } catch (SQLException throwables) {
          throwables.printStackTrace();
      } finally {
          con.disconnect();
      }
        return null;
    }

    public static String RemoveQuotasAndBrackets(String request) {
        request = request.replace("\"", "");
        request = request.replaceAll("\\[", "").replaceAll("\\]","");
        return request;
    }
}
