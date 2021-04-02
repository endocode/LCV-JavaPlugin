import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
public class sendPost {
  /*
  * Create the POST request
  */
  public static void main(String args[]) throws IOException, InterruptedException {

  HttpClient httpClient = new DefaultHttpClient();
  HttpPost httpPost = new HttpPost("http://0.0.0.0:8080/LicensesInput");
  // Request parameters and other properties.
  List<NameValuePair> params = new ArrayList<NameValuePair>();
  params.add(new BasicNameValuePair("InboundLicenses", "Apache 2.0,MIT License"));
  params.add(new BasicNameValuePair("OutboundLicense", "MIT"));
  try {
      httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
  } catch (UnsupportedEncodingException e) {
      // writing error to Log
      e.printStackTrace();
  }
  /*
   * Execute the HTTP Request
   */
  try {
      HttpResponse response = httpClient.execute(httpPost);
      HttpEntity respEntity = response.getEntity();

      if (respEntity != null) {
          // EntityUtils to get the response content
          String content =  EntityUtils.toString(respEntity);
      }
  } catch (ClientProtocolException e) {
      // writing exception to log
      e.printStackTrace();
  } catch (IOException e) {
      // writing exception to log
      e.printStackTrace();
  }
}
}
