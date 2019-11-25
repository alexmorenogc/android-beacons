package es.ulpgc.alexmoreno.beacons.register;

import java.lang.ref.WeakReference;

import es.ulpgc.alexmoreno.beacons.app.RepositoryInterface;
import es.ulpgc.alexmoreno.beacons.data.LoginUser;

public class RegisterPresenter implements RegisterContract.Presenter {

    public static String TAG = RegisterPresenter.class.getSimpleName();

    private WeakReference<RegisterContract.View> view;
    private RegisterViewModel viewModel;
    private RegisterContract.Model model;
    private RegisterContract.Router router;

    public RegisterPresenter(RegisterState state) {
        viewModel = state;
    }

    @Override
    public void injectView(WeakReference<RegisterContract.View> view) {
        this.view = view;
    }

    @Override
    public void injectModel(RegisterContract.Model model) {
        this.model = model;
    }

    @Override
    public void injectRouter(RegisterContract.Router router) {
        this.router = router;
    }


    @Override
    public void onRegisterButtonPressed(LoginUser user) {
        model.registerNewUser(user, new RepositoryInterface.RegisterNewUser() {
            @Override
            public void onNewUserRegistered(boolean error, boolean shortPassword, boolean allFieldsFilled) {
                if (!allFieldsFilled) {
                    view.get().displayFillAllFieldsMessage();
                } else if (shortPassword) {
                    view.get().displayPasswordTooShort();
                } else if (error){
                    view.get().displayError();
                } else {
                    view.get().displayRegisteredSuccessful();
                }
            }
        });
    }

    @Override
    public void routeToLogin() {
        router.navigateToLogin();
    }
}
