package airportsderby;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DataLayer {

    private Connection conn;

    DataLayer() throws SQLException {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app"); // The database URL may not be same for you, lookup the "Services" side-bar
        } catch (ClassNotFoundException ex) {
            throw new SQLException("Failed to load database driver", ex);
        }
    }

    public ArrayList<Airport> getAllAirports() throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM airports");
        ArrayList<Airport> result = new ArrayList<>();
        while (rs.next()) {
            Airport airport = new Airport(rs);
            result.add(airport);
        }
        return result;
    }

    public Airport getAirport(String code) throws SQLException {
        Airport airport = new Airport();
        String query = "SELECT * FROM airports WHERE code = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, code);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            airport.setCode(rs.getString("code"));
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

}
