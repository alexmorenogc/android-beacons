package es.ulpgc.alexmoreno.beacons.detail;

import java.lang.ref.WeakReference;

import es.ulpgc.alexmoreno.beacons.app.RepositoryInterface;
import es.ulpgc.alexmoreno.beacons.data.Local;
import es.ulpgc.alexmoreno.beacons.data.MasterItem;

public class DetailPresenter implements DetailContract.Presenter {

    public static String TAG = DetailPresenter.class.getSimpleName();

    private WeakReference<DetailContract.View> view;
    private DetailViewModel viewModel;
    private DetailContract.Model model;
    private DetailContract.Router router;

    public DetailPresenter(DetailState state) {
        viewModel = state;
    }

    @Override
    public void injectView(WeakReference<DetailContract.View> view) {
        this.view = view;
    }

    @Override
    public void injectModel(DetailContract.Model model) {
        this.model = model;
    }

    @Override
    public void injectRouter(DetailContract.Router router) {
        this.router = router;
    }

    /**
     * Method to recover the Local information from the model and setting the state with it
     */
    @Override
    public void fetchDetailData() {
        final MasterItem item = router.getMasterItemFromPreviousScreen();
        if (item != null) {
            model.loadDetailData(item.uuid, new RepositoryInterface.OnDetailDataFetchedCallback() {
                @Override
                public void setDetailData(Local local) {
                    viewModel.localSelected = local;
                    viewModel.localSelected.setUUID(item.uuid);
                    view.get().displayData(viewModel);
                }
            });
        } else {
            view.get().showErrorGettingItem();
        }
    }
}
