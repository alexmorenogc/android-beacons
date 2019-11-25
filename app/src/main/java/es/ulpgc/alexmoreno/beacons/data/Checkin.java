package es.ulpgc.alexmoreno.beacons.data;

/**
 * Class to control the beacons finding
 */
public class Checkin {
    private String userUUID;
    private String beaconUUID;
    private String placeUUID;
    private String locationLongitude;
    private String locationLatitude;
    private long time;

    public Checkin(String userUUID, String beaconUUID, String placeUUID, String locationLongitude, String locationLatitude, long time) {
        this.userUUID = userUUID;
        this.beaconUUID = beaconUUID;
        this.placeUUID = placeUUID;
        this.locationLongitude = locationLongitude;
        this.locationLatitude = locationLatitude;
        this.time = time;
    }

    public String getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationLongitude(String locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    public String getLocationLatitude() {
        return locationLatitude;
    }

    public void setLocationLatitude(String locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public String getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    public String getBeaconUUID() {
        return beaconUUID;
    }

    public void setBeaconUUID(String beaconUUID) {
        this.beaconUUID = beaconUUID;
    }

    public String getPlaceUUID() {
        return placeUUID;
    }

    public void setPlaceUUID(String placeUUID) {
        this.placeUUID = placeUUID;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
