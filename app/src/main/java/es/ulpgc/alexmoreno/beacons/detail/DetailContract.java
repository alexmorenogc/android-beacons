package es.ulpgc.alexmoreno.beacons.detail;

import java.lang.ref.WeakReference;
import es.ulpgc.alexmoreno.beacons.app.RepositoryInterface;
import es.ulpgc.alexmoreno.beacons.data.MasterItem;

interface DetailContract {

    interface View {
        void injectPresenter(Presenter presenter);
        void displayData(DetailViewModel viewModel);

        /**
         * Method to create an alert to notify the user that the local couldn't been got
         */
        void showErrorGettingItem();
    }

    interface Presenter {
        void injectView(WeakReference<View> view);
        void injectModel(Model model);
        void injectRouter(Router router);

        /**
         * Method to get the local from router and set on the view
         */
        void fetchDetailData();
    }

    interface Model {
        /**
         * Method for calling the model to get the local information
         * @param localUUID to be fetched
         * @param onDetailDataFetchedCallback when the information are ready
         */
        void loadDetailData(String localUUID, RepositoryInterface.OnDetailDataFetchedCallback onDetailDataFetchedCallback);
    }

    interface Router {
        void navigateToNextScreen();
        void passDataToNextScreen(DetailState state);
        DetailState getDataFromPreviousScreen();

        /**
         * Method to get the MasterItem from Mediator
         * @return the item
         */
        MasterItem getMasterItemFromPreviousScreen();
    }
}
