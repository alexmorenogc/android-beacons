package es.ulpgc.alexmoreno.beacons.login;

import android.app.Activity;

import java.lang.ref.WeakReference;

import es.ulpgc.alexmoreno.beacons.app.RepositoryInterface;
import es.ulpgc.alexmoreno.beacons.data.LoginUser;

public class LoginPresenter implements LoginContract.Presenter {

    public static String TAG = LoginPresenter.class.getSimpleName();

    private WeakReference<LoginContract.View> view;
    private LoginViewModel viewModel;
    private LoginContract.Model model;
    private LoginContract.Router router;

    public LoginPresenter(LoginState state) {
        viewModel = state;
    }

    @Override
    public void injectView(WeakReference<LoginContract.View> view) {
        this.view = view;
    }

    @Override
    public void injectModel(LoginContract.Model model) {
        this.model = model;
    }

    @Override
    public void injectRouter(LoginContract.Router router) {
        this.router = router;
    }

    @Override
    public void onLoginButtonPressed(LoginUser user) {
        model.loginUser(user, new RepositoryInterface.LoginUserInterface() {
            @Override
            public void onUserLoggedIn(boolean error, boolean shortPassword, boolean isEmailFilled) {
                if (!isEmailFilled) {
                    view.get().displayFillEmailAlert();
                } else if (shortPassword) {
                    view.get().displayPasswordTooShort();
                } else if (error) {
                    view.get().displayLoginErrorAlert();
                } else {
                    Activity activity = view.get().getActivity();
                    view.get().finishActivity();
                    router.navigateToNextScreen();
                }
            }
        });
    }

    @Override
    public void onRegisterButtonPressed() {
        router.navigateToRegisterScreen();
    }

    @Override
    public void onForgottenButtonPressed() {
        router.navigateToForgottenScreen();
    }
}
