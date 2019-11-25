package es.ulpgc.alexmoreno.beacons.beaconsFinder;

import android.location.Location;

import es.ulpgc.alexmoreno.beacons.app.RepositoryInterface;

public class BeaconsFinderModel implements BeaconsFinderContract.Model {

    public static String TAG = BeaconsFinderModel.class.getSimpleName();
    private RepositoryInterface repository;


    public BeaconsFinderModel(RepositoryInterface repository) {
        this.repository = repository;
    }

    @Override
    public void onBeaconFoundCreateCheckInAndGetPlace(String beaconUUID, Location location,
                                                      RepositoryInterface.OnPlaceFetchedCallback callback) {
        repository.registerNewCheckInAndGetPlace(beaconUUID, location, callback);
    }
}
