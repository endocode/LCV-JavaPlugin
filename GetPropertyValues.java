import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class GetPropertyValues {
	String result = "";
	InputStream inputStream;

	public String getPropValues(String s) throws IOException {

		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";

			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}

			Date time = new Date(System.currentTimeMillis());

			// get the property value and print it out
			switch (s) {
			  case "user":
					s = prop.getProperty("user");
			    break;
			  case "password":
					s = prop.getProperty("password");
			    break;
			  case "host":
			    s = prop.getProperty("host");
			    break;
			  case "port":
			    s = prop.getProperty("port");
			    break;
				case "db":
			    s = prop.getProperty("db");
			    break;
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return s;
	}
}
