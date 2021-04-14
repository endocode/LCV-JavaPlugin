import java.beans.Customizer;
import java.sql.*;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;
import org.postgresql.util.PGobject;


public class PostgreSqlConnector {
	public static void DBConnect() throws IOException {
		GetPropertyValues properties = new GetPropertyValues();

		String user = properties.getPropValues("user");
		String password = properties.getPropValues("password");
		String host = properties.getPropValues("host");
		String port = properties.getPropValues("port");
		String db = properties.getPropValues("db");

		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://"+host+":"+port+"/"+db+"", user, password)) {
			System.out.println("Java JDBC PostgreSQL Example");
			System.out.println("Connected to PostgreSQL database!");
			Statement statement = connection.createStatement();
			System.out.println("Reading metadata records...");
			ResultSet resultSet = statement.executeQuery("SELECT * FROM package_version");
			while (resultSet.next()) {
				String id = resultSet.getString("id");
				String metadata = resultSet.getString("metadata");
				PGobject jsonObject = new PGobject();
				jsonObject.setType("json");
				jsonObject.setValue(String.valueOf(metadata));
				// converting PGobject to JSONObject.
				String jsonText;
				jsonText = jsonObject.getValue();
				JSONObject json = new JSONObject(jsonText);
				// retrieving the JSONarray of InboundLicense
				JSONArray InboundLicense = json.getJSONObject("payload").getJSONObject("package_metadata").getJSONArray("InboundLicenses");
				System.out.println(InboundLicense);
			}
		} catch (SQLException e) {
			System.out.println("Connection failure.");
			e.printStackTrace();
		}
	}
}
