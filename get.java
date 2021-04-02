
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;



public class get {
  private static int sendGetRequest(String url) throws IOException, InterruptedException {
    var httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
    var request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
    var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    return response.statusCode();
}
  public static void main(String args[]) throws IOException, InterruptedException {
  // create a client
  HttpClient client = HttpClient.newHttpClient();
  HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create("http://webcode.me"))
          .GET() // GET is default
          .build();

  HttpResponse<Void> response = client.send(request,
      HttpResponse.BodyHandlers.discarding());
  System.out.println(response.statusCode());
  //String url = "http://webcode.me";
  String url = "http://0.0.0.0:8080/APIEndpoints";
  //int status = sendGetRequest(url);
  try {
    int status = sendGetRequest(url);
    if (status == 200) {
        print(url);
    } else if (status != 404) {
        print("HelloWorld not 200 code");
    }
} catch (IOException | InterruptedException e) {
    //logger.error("Error sending GET request to " + url, e);
    print(e);  }
  }
  private static void print(Exception e) {
  }
  private static void print(String url) {
    System.out.println(url);
  }
}
