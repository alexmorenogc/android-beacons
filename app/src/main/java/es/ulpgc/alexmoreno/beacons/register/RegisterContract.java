package es.ulpgc.alexmoreno.beacons.register;

import java.lang.ref.WeakReference;

import es.ulpgc.alexmoreno.beacons.app.RepositoryInterface;
import es.ulpgc.alexmoreno.beacons.data.LoginUser;

interface RegisterContract {

    interface View {
        void injectPresenter(Presenter presenter);

        /**
         * Method to ask the user to fill all the fields
         */
        void displayFillAllFieldsMessage();

        /**
         * Method to notify the user that the password has to be at least 6 characters
         */
        void displayPasswordTooShort();

        /**
         * Method to notify the user that the registration couldn't happen
         */
        void displayError();

        /**
         * Method to notify the user that the registration was successfully
         */
        void displayRegisteredSuccessful();
    }

    interface Presenter {
        void injectView(WeakReference<View> view);
        void injectModel(Model model);
        void injectRouter(Router router);

        /**
         * Method to notify the presenter that the register button was pressed
         * @param user to be registered
         */
        void onRegisterButtonPressed(LoginUser user);

        /**
         * Method to navigate to login activity after registration
         */
        void routeToLogin();
    }

    interface Model {
        /**
         * Method to notify Repository to register a new user
         * @param user to be registered
         * @param callback when the registration is successful or not
         */
        void registerNewUser(LoginUser user, RepositoryInterface.RegisterNewUser callback);
    }

    interface Router {
        void navigateToNextScreen();
        void passDataToNextScreen(RegisterState state);
        RegisterState getDataFromPreviousScreen();
        void navigateToLogin();
    }
}
