/**
 * @author Charbel
 * Jan 7, 2016 
 * semantic.store.sd.InsertRDFinStarDog.java
 */
package semantic.store.sd;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.openrdf.rio.RDFFormat;

import com.complexible.stardog.api.Connection;
import com.complexible.stardog.api.ConnectionConfiguration;
import com.complexible.stardog.api.admin.AdminConnection;
import com.complexible.stardog.api.admin.AdminConnectionConfiguration;

public class InsertRDFinStarDog {

	final static String DB_NAME = "test2";
	final static String USERNAME = "admin";
	final static String PASSWORD = "admin";

	final static String HTTP_STARDOG_ENPOINT= "http://localhost:5820/";
	final static String SNARL_STARDOG_ENPOINT= "snarl://localhost:5820/";

	public static void main(String[] args) throws Exception {

		createDB(HTTP_STARDOG_ENPOINT, DB_NAME, USERNAME, PASSWORD);
		InputStream inputStream = new FileInputStream(new File("C:\\test\\export.rdf")); 
		addData(SNARL_STARDOG_ENPOINT, DB_NAME, USERNAME, PASSWORD, inputStream);
	}

	public static void createDB(String httpEndpoint, String dB_name, String userName, String password)
	{
		AdminConnection aAdminConnection = AdminConnectionConfiguration.toServer(httpEndpoint).credentials(userName, password).connect(); 
		if (aAdminConnection.list().contains(dB_name)) {
			aAdminConnection.drop(dB_name);
		}
		aAdminConnection.createMemory(DB_NAME);
		System.out.println("DB Created !");
	}

	public static void addData(String snarlEndpoint, String dB_name, String userName, String password, InputStream inputStream)
	{
		Connection aConn = ConnectionConfiguration.from(snarlEndpoint+dB_name)	
				.credentials(userName, password).connect();

		aConn.begin();

		aConn.add().io().format(RDFFormat.RDFXML).stream(inputStream);
		aConn.commit();
		System.out.println("Data Added !");
	}


}