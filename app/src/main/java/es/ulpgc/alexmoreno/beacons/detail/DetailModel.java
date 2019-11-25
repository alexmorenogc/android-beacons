package es.ulpgc.alexmoreno.beacons.detail;

import es.ulpgc.alexmoreno.beacons.app.RepositoryInterface;

public class DetailModel implements DetailContract.Model {

    public static String TAG = DetailModel.class.getSimpleName();
    private RepositoryInterface repository;

    public DetailModel(RepositoryInterface repository) {
        this.repository = repository;
    }

    @Override
    public void loadDetailData(String localUUID, RepositoryInterface.OnDetailDataFetchedCallback callback) {
        repository.loadDetailData(localUUID, callback);
    }
}
