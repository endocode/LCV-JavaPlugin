import org.json.JSONArray;
import org.postgresql.util.PGobject;

import java.io.IOException;

public class main {
    private static String InboundLicenseClean;

    public static void main(String[] args) throws IOException, InterruptedException {
      String url = "http://0.0.0.0:8080/APIEndpoints";
      //retrieving InboundLicense array from Postgres
      JSONArray InboundLicense = PostgreSqlConnector.DBRetrieveInboundLicenses();
      // Manipulating the array before to pass it to LCV
      String InboundLicenseString = String.valueOf(InboundLicense);
      InboundLicenseClean = APICalls.RemoveQuotasAndBrackets(InboundLicenseString);
      // API call towards LCVServer
      APICalls.GetRequest(url);
      new PGobject();
      PGobject jsonAPIResponse = APICalls.LCVPostRequest(InboundLicenseClean);
      System.out.println(jsonAPIResponse);
        var b = PostgreSqlConnector.DBInsertLicenseComplianceAssessment(jsonAPIResponse);
        if (b==true){
            System.out.println("Update successfully inserted");
        }
    }
}
