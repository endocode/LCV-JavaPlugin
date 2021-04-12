import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class main {
    public static void main(String[] args) throws IOException, InterruptedException {
      String url = "http://0.0.0.0:8080/APIEndpoints";
      APICalls.GetRequest(url);
      APICalls.PostRequest();
      PostgreSqlConnector.DBConnect();
  }
}
