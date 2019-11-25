package es.ulpgc.alexmoreno.beacons.register;

import android.content.Intent;
import android.content.Context;

import es.ulpgc.alexmoreno.beacons.app.AppMediator;
import es.ulpgc.alexmoreno.beacons.login.LoginActivity;

public class RegisterRouter implements RegisterContract.Router {

    public static String TAG = RegisterRouter.class.getSimpleName();

    private AppMediator mediator;

    public RegisterRouter(AppMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void navigateToNextScreen() {
        Context context = mediator.getApplicationContext();
        Intent intent = new Intent(context, RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void passDataToNextScreen(RegisterState state) {
        mediator.setRegisterState(state);
    }

    @Override
    public RegisterState getDataFromPreviousScreen() {
        RegisterState state = mediator.getRegisterState();
        return state;
    }

    @Override
    public void navigateToLogin() {
        Context context = mediator.getApplicationContext();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
