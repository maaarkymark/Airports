package airportsrestful;

public class Airport {

    private static String code, name, city, country;
    private static int elevation;
    private static double latitude, longitude;

    // Default constructor
    Airport() {

    }

    // Constructor used when creating objects from user inputs
    Airport(String code, String name, String city, String country, int elevation, double latitude, double longitude) {
        this.code = code;
        this.name = name;
        this.city = city;
        this.country = country;
        this.elevation = elevation;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /*
    Airport(JSONObject jsonObject) {
        jsonObject.put("code", getCode());
        
        jsonObject.put("name", this.name);
        jsonObject.put("city", this.city);
        jsonObject.put("country", this.country);
        jsonObject.put("elevation", this.elevation);
        jsonObject.put("latitude", this.latitude);
        jsonObject.put("longitude", this.longitude);
        
    }*/
    public String toJSON() {
        return getCode() + " - " + getName() + " in " + getCity() + ", " + getCountry();
    }

    @Override
    public String toString() {
        return getCode() + " - " + getName() + " in " + getCity() + ", " + getCountry();
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {

        if ((!code.matches("[A-Z]+")) || (code.length()) != 3) {
            throw new IllegalArgumentException("Code must be uppercase letters only and 3 characters long");
        }
        this.code = code;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {

        if (name.length() < 3) {
            throw new IllegalArgumentException("Name must be at least 3 characters long");
        }
        this.name = name;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {

        if (city.length() < 3) {
            throw new IllegalArgumentException("City must be at least 3 characters long");
        }
        this.city = city;
    }

    /**
     * @return the country
     */
    public String getCountry() {

        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        if (country.length() < 3) {
            throw new IllegalArgumentException("Country must be at least 3 characters long");
        }
        this.country = country;
    }

    /**
     * @return the elevation
     */
    public int getElevation() {
        return elevation;
    }

    /**
     * @param elevation the elevation to set
     */
    public void setElevation(int elevation) {
        if (elevation < -10000 || elevation > 10000) {
            throw new IllegalArgumentException("Elevation must be between -10000 and 10000");
        }
        this.elevation = elevation;
    }

    /**
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(double latitude) {
        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("Latitude must be between -90 and 90");
        }
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(double longitude) {
        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Longitude must be between -180 and 180");
        }
        this.longitude = longitude;
    }

}
