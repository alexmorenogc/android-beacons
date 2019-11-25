package es.ulpgc.alexmoreno.beacons.forgotten;

import android.content.Intent;
import android.content.Context;
import es.ulpgc.alexmoreno.beacons.app.AppMediator;
import es.ulpgc.alexmoreno.beacons.login.LoginActivity;

public class ForgottenRouter implements ForgottenContract.Router {

    public static String TAG = ForgottenRouter.class.getSimpleName();
    private AppMediator mediator;

    public ForgottenRouter(AppMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void navigateToNextScreen() {
        Context context = mediator.getApplicationContext();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void passDataToNextScreen(ForgottenState state) {
        mediator.setForgottenState(state);
    }

    @Override
    public ForgottenState getDataFromPreviousScreen() {
        ForgottenState state = mediator.getForgottenState();
        return state;
    }
}
