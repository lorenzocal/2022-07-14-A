package it.polito.tdp.nyc.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import it.polito.tdp.nyc.model.Hotspot;
import java.util.*;

public class NYCDao {
	
	public List<Hotspot> getAllHotspot(){
		String sql = "SELECT * FROM nyc_wifi_hotspot_locations";
		List<Hotspot> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Hotspot(res.getInt("OBJECTID"), res.getString("Borough"),
						res.getString("Type"), res.getString("Provider"), res.getString("Name"),
						res.getString("Location"),res.getDouble("Latitude"),res.getDouble("Longitude"),
						res.getString("Location_T"),res.getString("City"),res.getString("SSID"),
						res.getString("SourceID"),res.getInt("BoroCode"),res.getString("BoroName"),
						res.getString("NTACode"), res.getString("NTAName"), res.getInt("Postcode")));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	
	public List<String> getAllBoroughs(){
		
		String sql = "SELECT DISTINCT Borough "
					+ "FROM nyc_wifi_hotspot_locations "
				    + "ORDER BY Borough ASC";
		
		List<String> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(res.getString("Borough"));
			}
			
			conn.close();
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("Errore nel DB", e);
		}

		return result;
	}
	
	public Map<String, Set<String>> getNTASSID(String borough){
		
		String sql = "SELECT DISTINCT NTACode, SSID "
				+ "FROM nyc_wifi_hotspot_locations "
				+ "WHERE Borough = ? "
				+ "ORDER BY NTACode ASC";
		
		Map<String, Set<String>> result = new HashMap<String, Set<String>>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, borough);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				
			    String NTACode = res.getString("NTACode");
			    String SSID = res.getString("SSID"); 
				
			    if (!result.containsKey(NTACode)) {
			    	result.put(NTACode, new HashSet<String>());
			    	result.get(NTACode).add(SSID);
			    }
			    else {
			    	if (!result.get(NTACode).contains(SSID)) {
			    		result.get(NTACode).add(SSID);
			    	}
			    }
			}
			
			conn.close();
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("Errore nel DB", e);
		}

		return result;
	}
	
	
}
