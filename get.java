
package com.twilio;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;


public class get {
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
  // create a request
  /*var request2 = HttpRequest.newBuilder(
         URI.create("https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY"))
     .header("accept", "application/json")
     .build();

  // use the client to send the request
  //var response2 = client.send(request2, new JsonBodyHandler<>(APOD.class));
  resp = client.send(request2, HttpResponse.BodyHandlers.discarding());
  // the response:
  System.out.println(resp.body().get().title);*/
  }
}
