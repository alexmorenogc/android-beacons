package es.ulpgc.alexmoreno.beacons.beaconsFinder;

import android.content.Intent;
import android.content.Context;

import es.ulpgc.alexmoreno.beacons.app.AppMediator;
import es.ulpgc.alexmoreno.beacons.master.MasterActivity;

public class BeaconsFinderRouter implements BeaconsFinderContract.Router {

    public static String TAG = BeaconsFinderRouter.class.getSimpleName();

    private AppMediator mediator;

    public BeaconsFinderRouter(AppMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void navigateToNextScreen() {
        Context context = mediator.getApplicationContext();
        Intent intent = new Intent(context, MasterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void passDataToNextScreen(BeaconsFinderState state) {
        mediator.setBeaconsFinderState(state);
    }

    @Override
    public BeaconsFinderState getDataFromPreviousScreen() {
        BeaconsFinderState state = mediator.getBeaconsFinderState();
        return state;
    }

    @Override
    public void passPlaceUUIDToMasterDetail(String placeUUID) {
        mediator.setPlaceUUID(placeUUID);
    }

}
