import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;


public class PostgreSqlConnector {
	public static void DBConnect() throws IOException {
		GetPropertyValues properties = new GetPropertyValues();

		String user = properties.getPropValues("user");
		String password = properties.getPropValues("password");
		String host = properties.getPropValues("host");
		String port = properties.getPropValues("port");
		String db = properties.getPropValues("db");
		JSONObject json = new JSONObject();



		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://"+host+":"+port+"/"+db+"", user, password)) {

			System.out.println("Java JDBC PostgreSQL Example");

			System.out.println("Connected to PostgreSQL database!");
			Statement statement = connection.createStatement();
			System.out.println("Reading metadata records...");
			//System.out.printf("%-30.30s  %-30.30s%n", "id", "metadata");
			ResultSet resultSet = statement.executeQuery("SELECT * FROM package_version");
			while (resultSet.next()) {
				//System.out.printf("%-30.30s  %-30.30s%n", resultSet.getString("id"), resultSet.getString("metadata"));
				String id = resultSet.getString("id");

				Object metadata = resultSet.getObject("metadata");
				//Object metadata = JSON.parse(resultSet.getString("metadata"));
				System.out.println("id:"+id);
				System.out.println("JSON Payload:"+metadata);
				String InboundLicenses = json.getJSONObject("input").getString("InboundLicenses");

			}
		} catch (SQLException e) {
			System.out.println("Connection failure.");
			e.printStackTrace();
		}
	}
}
