package es.ulpgc.alexmoreno.beacons.login;

import android.content.Intent;
import android.content.Context;
import es.ulpgc.alexmoreno.beacons.app.AppMediator;
import es.ulpgc.alexmoreno.beacons.beaconsFinder.BeaconsFinderActivity;
import es.ulpgc.alexmoreno.beacons.forgotten.ForgottenActivity;
import es.ulpgc.alexmoreno.beacons.register.RegisterActivity;

public class LoginRouter implements LoginContract.Router {

    public static String TAG = LoginRouter.class.getSimpleName();

    private AppMediator mediator;

    public LoginRouter(AppMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void navigateToNextScreen() {
        Context context = mediator.getApplicationContext();
        Intent intent = new Intent(context, BeaconsFinderActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void passDataToNextScreen(LoginState state) {
        mediator.setLoginState(state);
    }

    @Override
    public LoginState getDataFromPreviousScreen() {
        LoginState state = mediator.getLoginState();
        return state;
    }

    @Override
    public void navigateToRegisterScreen() {
        Context context = mediator.getApplicationContext();
        Intent intent = new Intent(context, RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void navigateToForgottenScreen() {
        Context context = mediator.getApplicationContext();
        Intent intent = new Intent(context, ForgottenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
