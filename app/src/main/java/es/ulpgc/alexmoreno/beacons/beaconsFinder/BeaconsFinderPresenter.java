package es.ulpgc.alexmoreno.beacons.beaconsFinder;


import android.location.Location;
import java.lang.ref.WeakReference;
import es.ulpgc.alexmoreno.beacons.app.RepositoryInterface;

public class BeaconsFinderPresenter implements BeaconsFinderContract.Presenter {

    public static String TAG = BeaconsFinderPresenter.class.getSimpleName();

    private WeakReference<BeaconsFinderContract.View> view;
    private BeaconsFinderViewModel viewModel;
    private BeaconsFinderContract.Model model;
    private BeaconsFinderContract.Router router;

    public BeaconsFinderPresenter(BeaconsFinderState state) {
        viewModel = state;
    }

    @Override
    public void injectView(WeakReference<BeaconsFinderContract.View> view) {
        this.view = view;
    }

    @Override
    public void injectModel(BeaconsFinderContract.Model model) {
        this.model = model;
    }

    @Override
    public void injectRouter(BeaconsFinderContract.Router router) {
        this.router = router;
    }

    @Override
    public void onBeaconFound(String beaconUUID, Location location) {
        model.onBeaconFoundCreateCheckInAndGetPlace(beaconUUID, location, new RepositoryInterface.OnPlaceFetchedCallback() {
            @Override
            public void setPlaceUUID(String placeUUID, boolean error) {
                viewModel.placeUUID = placeUUID;
                if (error) {
                    view.get().showErrorLoadingPlace();
                }
                view.get().enableToMasterButtonAndStopSearch();
            }
        });
    }

    @Override
    public void onToMasterPressed() {
        router.passPlaceUUIDToMasterDetail(viewModel.placeUUID);
        router.navigateToNextScreen();
    }

}
