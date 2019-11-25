package es.ulpgc.alexmoreno.beacons.master;

import java.lang.ref.WeakReference;
import es.ulpgc.alexmoreno.beacons.data.MasterItem;
import es.ulpgc.alexmoreno.beacons.app.RepositoryInterface;

interface MasterContract {

    interface View {
        void injectPresenter(Presenter presenter);

        /**
         * Method to set the list
         * @param viewModel to get the list
         */
        void displayData(MasterViewModel viewModel);
    }

    interface Presenter {
        void injectView(WeakReference<View> view);
        void injectModel(Model model);
        void injectRouter(Router router);

        /**
         * Method to set the item selected in the router and ask to change the activity
         * @param item selected
         */
        void selectMasterItemData(MasterItem item);

        /**
         * Method to get the items from the model
         */
        void fetchMasterItemsData();
    }

    interface Model {
        /**
         * Method to ask the repository the list
         * @param placeUUID used to filter the list
         * @param callback when the list has been loaded
         */
        void loadMasterItems(String placeUUID, RepositoryInterface.OnMasterItemListFetchedCallback callback);
    }

    interface Router {
        void navigateToNextScreen();
        void passDataToNextScreen(MasterState state);
        MasterState getDataFromPreviousScreen();

        /**
         * Method used to set the item in Mediator
         * @param item to be setted
         */
        void passMasterItemToDetailScreen(MasterItem item);

        /**
         * Method used to get the placeUUID from Mediator
         * @return placeUUID
         */
        String getPlaceUUIDFromBeaconsFinder();
    }
}
