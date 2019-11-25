package es.ulpgc.alexmoreno.beacons.app;

import android.location.Location;

import java.util.List;

import es.ulpgc.alexmoreno.beacons.data.Local;
import es.ulpgc.alexmoreno.beacons.data.LoginUser;
import es.ulpgc.alexmoreno.beacons.data.MasterItem;

public interface RepositoryInterface {
    /* Register interfaces */
    interface RegisterNewUser {
        /**
         * Callback when the user has been created
         * @param error to check if has existed some error
         * @param shortPassword to check if the password has more than 6 characters
         * @param allFieldsFilled to check if the user has filled all the fields
         */
        void onNewUserRegistered(boolean error, boolean shortPassword, boolean allFieldsFilled);
    }

    /**
     * Method to register a new user into Firebase
     * @param user object with all the parameters needed to register the user
     * @param callback used when the registration was made
     */
    void registerNewUser(LoginUser user, RepositoryInterface.RegisterNewUser callback);

    /* Login interfaces */
    interface LoginUserInterface {
        /**
         * Callback when the user has been logged in
         * @param error to check if has existed some error
         * @param shortPassword to check if the password has more than 6 characters
         * @param isEmailFilled to check if the user has filled all the fields
         */
        void onUserLoggedIn(boolean error, boolean shortPassword, boolean isEmailFilled);
    }

    /**
     * Method to login into Firebase
     * @param user object with all the parameters needed to login
     * @param callback used when the login was made
     */
    void logInUser(LoginUser user, LoginUserInterface callback);

    interface CheckIfUserIsLoggedIn {
        /**
         * Callback to control if the user is logged in
         * @param isLoggedIn boolean to check if the user is logged in or not
         */
        void onUserIsLoggedIn(boolean isLoggedIn);
    }

    /**
     * Method to check if the user is logged into Firebase
     * @param callback used when is checked into Firebase
     */
    void isUserLoggedIn(RepositoryInterface.CheckIfUserIsLoggedIn callback);

    /* ForgottenPassword interfaces */
    interface NewPasswordInterface {
        /**
         * Callback to control the password sending
         * @param error to check if has existed some error
         * @param isEmailFilled to check if the user has filled the email
         */
        void onNewPasswordSent(boolean error, boolean isEmailFilled);
    }

    /**
     * Method to send to Firebase the command to send the mail to recover the password
     * @param userMail used by the user
     * @param callback used to control the answer from Firebase
     */
    void sendNewPassword(String userMail, RepositoryInterface.NewPasswordInterface callback);

    /* BeaconsFinder interfaces */
    interface OnPlaceFetchedCallback {
        /**
         * Callback to set the placeUUID into the Mediator to being used in master-detail
         * @param placeUUID found in the beacon
         * @param error to check if the action has been executed successfully
         */
        void setPlaceUUID(String placeUUID, boolean error);
    }

    /**
     * Method to get the placeUUID into Firebase and save the Checkin in this beacon
     * @param beaconUUID to find in Firebase the placeUUID associated
     * @param location of the user to be saved in Firebase
     * @param callback when the placeUUID has been got in Firebase
     */
    void registerNewCheckInAndGetPlace(String beaconUUID, Location location,
                                       RepositoryInterface.OnPlaceFetchedCallback callback);

    /* Detail interfaces */
    interface OnDetailDataFetchedCallback {
        /**
         * Callback to set the local information in Detail
         * @param local information to be setted in Detail
         */
        void setDetailData(Local local);
    }

    /**
     * Method to load the local data into Firebase
     * @param localUUID used to find the local
     * @param callback when the local has been got
     */
    void loadDetailData(String localUUID, OnDetailDataFetchedCallback callback);

    /* Master interfaces */
    interface OnMasterItemListFetchedCallback {
        /**
         * Callback to set the items in Master
         * @param itemList list of items in Master Screen
         */
        void setMasterItemList(List<MasterItem> itemList);
    }

    /**
     * Method to get the local list from Firebase
     * @param placeUUID used to find the local list
     * @param callback when the list has been got
     */
    void loadMasterItems(String placeUUID, OnMasterItemListFetchedCallback callback);
}
