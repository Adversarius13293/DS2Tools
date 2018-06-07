package adver.sarius.ds2tools.datacollector;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * Diese Klasse soll Daten von der live DS2 Website sammeln und so vorbereiten,
 * dass man sie in die eigene lokale DS2 Datenbank einfügen kann.
 */
public class DataCollector {
	// TODO:
	// (ammo)
	// ammo_flags
	// (base_types)
	// ?building_alternativebilder?
	// (buildings)
	// (config)
	// config_felsbrocken
	// config_felsbrocken_systems
	// cores
	// ?dynamic_content?
	// ?dynamic_jn_config?
	// ?dynamic_jn_config_startsystems?
	// ?dynamic_jn_config_zielsystems?
	// ?dynamic_jumpnode?
	// (forschungen)
	// (fraktions_gui_eintrag)
	// (fraktions_gui_eintrag_seiten)
	// ?fz?
	// (global_sectortemplates)
	// (gtu_warenkurse)
	// gui_help
	// (inttutorial)
	// (items)
	// (items_build)
	// (items_schiffstyp_modifikation)
	// (jumpnodes)
	// (medal)
	// (module_slot)
	// ?permission?
	// (rang)
	// (rasse)
	// schiffsbauplan_forschungen
	// (schiffsmodul_slots)
	// (schiffstyp_modifikation)
	// (schiffstyp_modifikation_flags)
	// (schiffswaffenkonfiguration)
	// (ships_baubar)
	// (ship_types)
	// (smilies)
	// (systems)
	// unit_types
	// (upgrade_info)
	// (upgrade_maxvalues)
	// (weapon)
	// (weapon_flags)
	// (weapon_munition)

	private static List<String> cookies;
	private static HttpURLConnection connection;
	
	// TODO: Does not work, can't login properly
	public static void main(String[] args) throws Exception{
		System.exit(0);;
		try {
//			URL url = new URL("http://localhost:8080/driftingsouls/ds?module=client#/map/2/1/1?admin=false");
			String urlString = "http://localhost:8080/driftingsouls/ds";
			URL url = new URL(urlString);
			
			CookieHandler.setDefault(new CookieManager());
			
			String page = getPageContent(urlString);
			System.out.println(page);
			
			String params = "username="+ URLEncoder.encode("Adversarius", "UTF-8") + "&password=" + URLEncoder.encode("Adversarius", "UTF-8");
						
			sendPost(urlString, params);			
			
			System.out.println(getPageContent(urlString+"?module=client#/map/2/1/1?admin=false"));
			
			
			if(true) return;			
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setUseCaches(true);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//			connection.connect();
			
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
			
			String content = "username="+ URLEncoder.encode("Adversarius", "UTF-8") + "&password=" + URLEncoder.encode("Adversarius", "UTF-8");
			writer.write(content);
			writer.flush();
			writer.close();
			System.out.println(connection.getResponseCode());
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			String line;
			while ((line = reader.readLine()) != null){
				System.out.println(line);
			}
			reader.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void sendPost(String url, String postParams) throws Exception {

		URL obj = new URL(url);
		connection = (HttpURLConnection) obj.openConnection();

		// Acts like a browser
		connection.setUseCaches(false);
		connection.setRequestMethod("POST");
		for (String cookie : cookies) {
			connection.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
		}
		connection.setRequestProperty("Connection", "keep-alive");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.setRequestProperty("Content-Length", Integer.toString(postParams.length()));

		connection.setDoOutput(true);
		connection.setDoInput(true);

		// Send post request
		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
		wr.writeBytes(postParams);
		wr.flush();
		wr.close();

		int responseCode = connection.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + postParams);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = 
	             new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		System.out.println(response.toString());
	  }	
	
	
	
	private static String getPageContent(String url) throws MalformedURLException, IOException {
		URL u = new URL(url);
		connection = (HttpURLConnection) u.openConnection();
		// default is GET
		connection.setRequestMethod("GET");

		connection.setUseCaches(false);
		if (cookies != null) {
			for (String cookie : cookies) {
				connection.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
			}
		}
		int responseCode = connection.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		
		BufferedReader in = 
	            new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// Get the response cookies
		cookies = connection.getHeaderFields().get("Set-Cookie");

		return response.toString();		
	}
}