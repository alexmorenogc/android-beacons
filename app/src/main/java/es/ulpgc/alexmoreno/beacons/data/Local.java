package es.ulpgc.alexmoreno.beacons.data;

/**
 * Class to get the locals from Firebase
 */
public class Local {
    private String UUID;
    private String name;
    private String category;
    private String description;
    private String phone;
    private String website;
    private Double longitude;
    private Double latitude;

    public Local(String UUID,
                 String name,
                 String category,
                 String description,
                 String phone,
                 String website,
                 Double longitude,
                 Double latitude) {
        this.UUID = UUID;
        this.name = name;
        this.category = category;
        this.description = description;
        this.phone = phone;
        this.website = website;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

}
