package es.ulpgc.alexmoreno.beacons.master;

import android.content.Intent;
import android.content.Context;

import es.ulpgc.alexmoreno.beacons.app.AppMediator;
import es.ulpgc.alexmoreno.beacons.data.MasterItem;
import es.ulpgc.alexmoreno.beacons.detail.DetailActivity;

public class MasterRouter implements MasterContract.Router {

    public static String TAG = MasterRouter.class.getSimpleName();

    private AppMediator mediator;

    public MasterRouter(AppMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void navigateToNextScreen() {
        Context context = mediator.getApplicationContext();
        Intent intent = new Intent(context, DetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void passDataToNextScreen(MasterState state) {
        mediator.setMasterState(state);
    }

    @Override
    public MasterState getDataFromPreviousScreen() {
        MasterState state = mediator.getMasterState();
        return state;
    }

    @Override
    public void passMasterItemToDetailScreen(MasterItem item) {
        mediator.setMasterItem(item);
    }

    @Override
    public String getPlaceUUIDFromBeaconsFinder() {
        String placeUUID = mediator.getPlaceUUID();
        return placeUUID;
    }
}
