package es.ulpgc.alexmoreno.beacons.forgotten;

import android.app.Activity;
import java.lang.ref.WeakReference;
import es.ulpgc.alexmoreno.beacons.app.RepositoryInterface;

interface ForgottenContract {

    interface View {
        void injectPresenter(Presenter presenter);
        Activity getActivity();
        void finishActivity();

        /**
         * Method to ask the user to fill email
         */
        void displayFillEmailAlert();

        /**
         * Method to ask the user to fill password
         */
        void displaySendPasswordErrorAlert();
    }

    interface Presenter {
        void injectView(WeakReference<View> view);
        void injectModel(Model model);
        void injectRouter(Router router);

        /**
         * Method called by view to notify the button has been pressed
         * @param userMail written by the user
         */
        void onSendPasswordButtonPressed(String userMail);
    }

    interface Model {
        /**
         * Method to notify to Repository asking for the new password
         * @param userMail asking for the new password
         * @param callback when the new password has been sent
         */
        void sendNewPassword(String userMail, RepositoryInterface.NewPasswordInterface callback);
    }

    interface Router {
        void navigateToNextScreen();
        void passDataToNextScreen(ForgottenState state);
        ForgottenState getDataFromPreviousScreen();
    }
}
