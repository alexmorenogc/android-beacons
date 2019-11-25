package es.ulpgc.alexmoreno.beacons.login;

import android.app.Activity;
import java.lang.ref.WeakReference;
import es.ulpgc.alexmoreno.beacons.app.RepositoryInterface;
import es.ulpgc.alexmoreno.beacons.data.LoginUser;

interface LoginContract {

    interface View {
        void injectPresenter(Presenter presenter);
        Activity getActivity();
        void finishActivity();

        /**
         * Method to ask the user to fill email
         */
        void displayFillEmailAlert();

        /**
         * Method to notify the user that the password has to be at least 6 characters
         */
        void displayPasswordTooShort();

        /**
         * Method to notify the user that the login couldn't happen
         */
        void displayLoginErrorAlert();
    }

    interface Presenter {
        void injectView(WeakReference<View> view);
        void injectModel(Model model);
        void injectRouter(Router router);

        /**
         * Method to notify the presenter that the login button was pressed
         * @param user mail and password written by the user
         */
        void onLoginButtonPressed(LoginUser user);

        /**
         * Method to notify the presenter that the register button was pressed
         */
        void onRegisterButtonPressed();

        /**
         * Method to notify the presenter that the forgotten button was pressed
         */
        void onForgottenButtonPressed();
    }

    interface Model {
        /**
         * Method to notify the Repository to login the user
         * @param user to be logged
         * @param callback when the user has been logged or not
         */
        void loginUser(LoginUser user, RepositoryInterface.LoginUserInterface callback);
    }

    interface Router {
        void navigateToNextScreen();
        void passDataToNextScreen(LoginState state);
        LoginState getDataFromPreviousScreen();
        void navigateToRegisterScreen();
        void navigateToForgottenScreen();
    }
}
