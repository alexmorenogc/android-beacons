package es.ulpgc.alexmoreno.beacons.beaconsFinder;

import android.location.Location;
import java.lang.ref.WeakReference;
import es.ulpgc.alexmoreno.beacons.app.RepositoryInterface;

interface BeaconsFinderContract {

    interface View {
        void injectPresenter(Presenter presenter);

        /**
         * Method to create an Error Alert because the beacon Found it is not in Firebase
         */
        void showErrorLoadingPlace();

        /**
         * Method to stop the beacon search and enable the button to show the locals in master
         */
        void enableToMasterButtonAndStopSearch();
    }

    interface Presenter {
        void injectView(WeakReference<View> view);
        void injectModel(Model model);
        void injectRouter(Router router);

        /**
         * Method called by the view when one beacon has been found sending to model to find its
         * information and save the Check-in
         * @param beaconUUID uuid of the beacon
         * @param location of the user
         */
        void onBeaconFound(String beaconUUID, Location location);

        /**
         * Method called by the view when the button toMaster has been pressed, it is save the
         * placeUUID into the Mediator (by the Router) and call an intent
         */
        void onToMasterPressed();
    }

    interface Model {
        /**
         * Method to send to Repository the information to save the Check-in and get the placeUUID
         * @param beaconUUID to be found into Firebase
         * @param location of the user
         * @param callback when the Check-in has been saved and the place UUID as well
         */
        void onBeaconFoundCreateCheckInAndGetPlace(String beaconUUID, Location location,
                                                   RepositoryInterface.OnPlaceFetchedCallback callback);
    }

    interface Router {
        void navigateToNextScreen();
        void passDataToNextScreen(BeaconsFinderState state);
        BeaconsFinderState getDataFromPreviousScreen();

        /**
         * Method to save placeUUID into Mediator
         * @param placeUUID to be used in master
         */
        void passPlaceUUIDToMasterDetail(String placeUUID);
    }
}
