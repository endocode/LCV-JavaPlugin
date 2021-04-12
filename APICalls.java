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
public class APICalls {
    private static HttpURLConnection con;
    private static int sendGetRequest(String url) throws IOException, InterruptedException {
      var httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
      var request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
      var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
      return response.statusCode();
    }
    public static void GetRequest(String url) throws IOException, InterruptedException {
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
    public static void PostRequest() throws IOException {
      // these strings should be retrieved from the metadata db
      String InboundLicensesList = "MIT,Apache-2.0";
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
          System.out.println(content.toString());
      } finally {
          con.disconnect();
      }
  }
    public static void PostRequest_2ndMethod() throws IOException {
      String urlParameters2  = "InboundLicenses=MIT&OutboundLicense=MIT";
      byte[] postData2       = urlParameters2.getBytes( StandardCharsets.UTF_8 );
      int    postDataLength = postData2.length;
      var    request        = "http://0.0.0.0:8080/LicensesInputSPDX";
      try {
          var myurl2 = new URL(request);
          //HttpURLConnection conn = (HttpURLConnection) url2.openConnection();
          con = (HttpURLConnection) myurl2.openConnection();
          con.setDoOutput( true );
          con.setInstanceFollowRedirects( false );
          con.setRequestMethod( "POST" );
          con.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
          con.setRequestProperty( "charset", "utf-8");
          con.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
          con.setUseCaches( false );
          try( DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
            wr.write(postData2);
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
          System.out.println(content.toString());
      } finally {
          con.disconnect();
      }
    }
}
