import java.sql.*;
import java.io.IOException;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;
import org.postgresql.util.PGobject;


public class PostgreSqlConnector {
	private static ResourceBundle resultSet;

	public static Connection DBConnection() throws IOException, SQLException {
		GetPropertyValues properties = new GetPropertyValues();
		String user = properties.getPropValues("user");
		String password = properties.getPropValues("password");
		String host = properties.getPropValues("host");
		String port = properties.getPropValues("port");
		String db = properties.getPropValues("db");
		System.out.println("Connecting to Postgres ... ");
		Connection connection = DriverManager.getConnection("jdbc:postgresql://"+host+":"+port+"/"+db+"", user, password);
		System.out.println("Connected to PostgreSQL database!");
		return connection;
	}

	public static JSONArray DBRetrieveInboundLicenses() throws IOException {
		try (Connection connection = PostgreSqlConnector.DBConnection()){
			ResultSet resultSet;
			Statement statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM package_version WHERE id = 2");
			if (resultSet.next()) {
				JSONArray InboundLicense = null;
				String id = resultSet.getString("id");
				String metadata = resultSet.getString("metadata");
				PGobject jsonObject = new PGobject();
				jsonObject.setType("json");
				jsonObject.setValue(String.valueOf(metadata));
				// converting PGobject to JSONObject.
				String jsonText;
				jsonText = jsonObject.getValue();
				JSONObject json = new JSONObject(jsonText);
				// retrieving the JSON array of InboundLicense
				InboundLicense = json.getJSONObject("payload").getJSONObject("package_metadata").getJSONArray("InboundLicenses");
				return InboundLicense;
			}
		} catch (SQLException e) {
			System.out.println("Connection failure.");
			e.printStackTrace();
		}
		return null;
	}

	// in input a json
	public static boolean DBInsertLicenseComplianceAssessment(PGobject jsonAPIResponse) throws IOException {
		String query = "UPDATE package_version SET \"LicenseComplianceAssessment\" = ? WHERE id=2" ;
		try (Connection connection = PostgreSqlConnector.DBConnection()){
			PreparedStatement statement = connection.prepareStatement(query);
			jsonAPIResponse.setType("json");
			jsonAPIResponse.setValue(jsonAPIResponse.toString());
			statement.setObject(1,jsonAPIResponse);
			return statement.executeUpdate() > 0;
			
			} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return isaBoolean();
	}

	private static boolean isaBoolean() {
		return false;
	}
}
