package es.ulpgc.alexmoreno.beacons.master;

import es.ulpgc.alexmoreno.beacons.app.RepositoryInterface;

public class MasterModel implements MasterContract.Model {

    public static String TAG = MasterModel.class.getSimpleName();

    private RepositoryInterface repository;

    public MasterModel(RepositoryInterface repository) {
        this.repository = repository;
    }

    @Override
    public void loadMasterItems(String placeUUID, RepositoryInterface.OnMasterItemListFetchedCallback callback) {
        repository.loadMasterItems(placeUUID, callback);
    }

}
