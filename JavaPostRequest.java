import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
public class JavaPostRequest {

    private static HttpURLConnection con;

    public static void main(String[] args) throws IOException {
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
            con.setRequestProperty( "charset", "utf-8");

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
}
