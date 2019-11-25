package es.ulpgc.alexmoreno.beacons.forgotten;

import android.app.Activity;
import java.lang.ref.WeakReference;
import es.ulpgc.alexmoreno.beacons.app.RepositoryInterface;

public class ForgottenPresenter implements ForgottenContract.Presenter {

    public static String TAG = ForgottenPresenter.class.getSimpleName();

    private WeakReference<ForgottenContract.View> view;
    private ForgottenViewModel viewModel;
    private ForgottenContract.Model model;
    private ForgottenContract.Router router;

    public ForgottenPresenter(ForgottenState state) {
        viewModel = state;
    }

    @Override
    public void injectView(WeakReference<ForgottenContract.View> view) {
        this.view = view;
    }

    @Override
    public void injectModel(ForgottenContract.Model model) {
        this.model = model;
    }

    @Override
    public void injectRouter(ForgottenContract.Router router) {
        this.router = router;
    }

    @Override
    public void onSendPasswordButtonPressed(String userMail) {
        model.sendNewPassword(userMail, new RepositoryInterface.NewPasswordInterface() {
            @Override
            public void onNewPasswordSent(boolean error, boolean isEmailFilled) {
                if (!isEmailFilled) {
                    view.get().displayFillEmailAlert();
                } else if (error) {
                    view.get().displaySendPasswordErrorAlert();
                } else {
                    Activity activity = view.get().getActivity();
                    view.get().finishActivity();
                    router.navigateToNextScreen();
                }
            }
        });
    }
}
