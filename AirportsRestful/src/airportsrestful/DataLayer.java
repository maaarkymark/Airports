package airportsrestful;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DataLayer {

    private static Airport airport = new Airport();
    private static JSONObject airportJSONobj = new JSONObject();

    private final static String BASE_URL = "http://cccs301.szopski.com/api/v1";

    public static String getDataFromUrl(String url) throws MalformedURLException, IOException, ParseException {
        URL u = new URL(BASE_URL + url);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        conn.setRequestMethod("GET");
        // we only really open connection in the line below
        if (conn.getResponseCode() / 100 != 2) {
            throw new RuntimeException("Failed with HTTP error code " + conn.getResponseCode());
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String output = "";
        String line = null;
        while ((line = br.readLine()) != null) {
            output += line;
        }
        conn.disconnect();

        try {
            JSONParser parser = new JSONParser();
            JSONArray array = (JSONArray) parser.parse(output);
            for (int i = 0; i < array.size(); i++) {
                JSONObject airport = (JSONObject) array.get(i);
                String code = (String) airport.get("code");
                System.out.printf("Airport: %s\n", code);
            }
            System.out.println(array.size());
        } catch (ClassCastException ex) {
            ex.printStackTrace();
            // use exception chaining: IOException was originally caused by ClassCastException
            throw new IOException(
                    "Class cast exception while parsing JSON. Possibly invalid JSON structure", ex);
        }
        return output;
    }

    public static void sendDataToUrl(String url) throws MalformedURLException, IOException {
        airportJSONobj.put("code", airport.getCode());
        airportJSONobj.put("name", airport.getName());
        airportJSONobj.put("city", airport.getCity());
        airportJSONobj.put("country", airport.getCountry());
        airportJSONobj.put("elevation", airport.getElevation());
        airportJSONobj.put("latitude", airport.getLatitude());
        airportJSONobj.put("longitude", airport.getLongitude());
        String data = airportJSONobj.toJSONString();
        URL u = new URL(BASE_URL + url);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());
        outputStream.writeBytes(data);
        outputStream.flush();
        outputStream.close();
        // we only really connection in the line below
        if (conn.getResponseCode() / 100 != 2) {
            throw new RuntimeException("Failed with HTTP error code " + conn.getResponseCode());
        }
        conn.disconnect();

    }

    public static void removeDataFromUrl(String url) throws MalformedURLException, IOException {
        URL u = new URL(BASE_URL + url);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        conn.setRequestMethod("DELETE");
        conn.setDoOutput(true);
        // we only really connection in the line below
        if (conn.getResponseCode() / 100 != 2) {
            throw new RuntimeException("Failed with HTTP error code " + conn.getResponseCode());
        }
        conn.disconnect();

    }

    public static void updateDataFromUrl(String url) throws MalformedURLException, IOException {
        airportJSONobj.put("name", airport.getName());
        airportJSONobj.put("city", airport.getCity());
        airportJSONobj.put("country", airport.getCountry());
        airportJSONobj.put("elevation", airport.getElevation());
        airportJSONobj.put("latitude", airport.getLatitude());
        airportJSONobj.put("longitude", airport.getLongitude());
        String data = airportJSONobj.toJSONString();
        URL u = new URL(BASE_URL + url);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        conn.setRequestMethod("PUT");
        conn.setDoOutput(true);
        DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());
        outputStream.writeBytes(data);
        outputStream.flush();
        outputStream.close();
        // we only really connection in the line below
        if (conn.getResponseCode() / 100 != 2) {
            throw new RuntimeException("Failed with HTTP error code " + conn.getResponseCode());
        }
        conn.disconnect();

    }

    public ArrayList<Airport> getAllAirports() throws MalformedURLException, ParseException, IOException {
        String url = "/airports";
        ArrayList<Airport> result = new ArrayList<>();
        for (int row = 0; row > result.size(); row++) {
            Airport airport = new Airport();
            //getDataFromUrl(url);
            result.add(airport);
        }
        return result;
    }

    public static String getDataFromUrl2(String url) throws MalformedURLException, IOException, ParseException {
        URL u = new URL(BASE_URL + url);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        conn.setRequestMethod("GET");
        // we only really open connection in the line below
        if (conn.getResponseCode() / 100 != 2) {
            throw new RuntimeException("Failed with HTTP error code " + conn.getResponseCode());
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String output = "";
        String line = null;
        while ((line = br.readLine()) != null) {
            output += line;
        }
        conn.disconnect();

        try {
            JSONParser parser = new JSONParser();
            ArrayList<String> listData = new ArrayList<String>();
            ArrayList<Airport> listAirport = new ArrayList<Airport>();
            JSONArray array = (JSONArray) parser.parse(output);
            if (array != null) {
                for (int i = 0; i < array.size(); i++) {
                    listData.add(array.get(i).toString());
                }
            }
            for (String ap : listData) {

            }
        } catch (ClassCastException ex) {
            ex.printStackTrace();
            // use exception chaining: IOException was originally caused by ClassCastException
            throw new IOException(
                    "Class cast exception while parsing JSON. Possibly invalid JSON structure", ex);
        }
        return output;
    }

    /*
    public Airport getAirport(String code) throws SQLException {
        Airport airport = new Airport();
        String query = "SELECT * FROM airports WHERE code = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, code);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            airport.setName(rs.getString("name"));
            airport.setCity(rs.getString("city"));
            airport.setCountry(rs.getString("country"));
            airport.setElevation(rs.getInt("elevation"));
            airport.setLatitude(rs.getDouble("latitude"));
            airport.setLongitude(rs.getDouble("longitude"));
        }
        return airport;
    }

    public void createAirport(Airport airport) throws SQLException {
        String query = "INSERT INTO airports VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, airport.getCode());
        pstmt.setString(2, airport.getName());
        pstmt.setString(3, airport.getCity());
        pstmt.setString(4, airport.getCountry());
        pstmt.setInt(5, airport.getElevation());
        pstmt.setDouble(6, airport.getLatitude());
        pstmt.setDouble(7, airport.getLongitude());
        pstmt.execute(); // May throw SQLException
    }

    public void updateAirport(Airport airport) throws SQLException {
        String query = "UPDATE airports SET name = ?, city = ?, country = ?, elevation = ?, latitude = ?, longitude = ? WHERE code = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, airport.getName());
        pstmt.setString(2, airport.getCity());
        pstmt.setString(3, airport.getCountry());
        pstmt.setInt(4, airport.getElevation());
        pstmt.setDouble(5, airport.getLatitude());
        pstmt.setDouble(6, airport.getLongitude());
        pstmt.setString(7, airport.getCode());
        pstmt.executeUpdate(); // May throw SQLException
    }

    public void deleteAirport(String code) throws SQLException {
        String query = "DELETE FROM airports WHERE code = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, code);
        pstmt.executeUpdate(); // May throw SQLException
    }

    public ArrayList<Airport> getAllAirportsLatLong() throws SQLException {
        String query = "SELECT latitude, longitude FROM airports";
        PreparedStatement pstmt = conn.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();
        ArrayList<Airport> result = new ArrayList<>();
        while (rs.next()) {
            Airport airport = new Airport();
            airport.setLatitude(rs.getDouble("latitude"));
            airport.setLongitude(rs.getDouble("longitude"));
            result.add(airport);
        }
        return result;
    }
     */
}
