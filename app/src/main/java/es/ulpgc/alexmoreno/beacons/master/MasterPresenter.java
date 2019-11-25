package es.ulpgc.alexmoreno.beacons.master;

import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.List;

import es.ulpgc.alexmoreno.beacons.data.MasterItem;
import es.ulpgc.alexmoreno.beacons.app.RepositoryInterface;

public class MasterPresenter implements MasterContract.Presenter {

    public static String TAG = MasterPresenter.class.getSimpleName();

    private WeakReference<MasterContract.View> view;
    private MasterViewModel viewModel;
    private MasterContract.Model model;
    private MasterContract.Router router;

    public MasterPresenter(MasterState state) {
        viewModel = state;
    }

    @Override
    public void injectView(WeakReference<MasterContract.View> view) {
        this.view = view;
    }

    @Override
    public void injectModel(MasterContract.Model model) {
        this.model = model;
    }

    @Override
    public void injectRouter(MasterContract.Router router) {
        this.router = router;
    }

    @Override
    public void selectMasterItemData(MasterItem item) {
        router.passMasterItemToDetailScreen(item);
        router.navigateToNextScreen();
    }

    @Override
    public void fetchMasterItemsData() {
        String placeUUID = router.getPlaceUUIDFromBeaconsFinder();
        if (placeUUID == null) {
            viewModel.placeUUID = "notFound";
        } else {
            viewModel.placeUUID = placeUUID;
        }
        Log.d(TAG, "fetchMasterItemsData: placeUUID:" + placeUUID);
        model.loadMasterItems(viewModel.placeUUID, new RepositoryInterface.OnMasterItemListFetchedCallback() {
            @Override
            public void setMasterItemList(List<MasterItem> itemList) {
                viewModel.masterItemList = itemList;
                view.get().displayData(viewModel);
            }
        });
    }

}
