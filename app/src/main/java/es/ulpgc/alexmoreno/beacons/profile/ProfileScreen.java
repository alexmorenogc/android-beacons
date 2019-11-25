package es.ulpgc.alexmoreno.beacons.profile;

import java.lang.ref.WeakReference;
import androidx.fragment.app.FragmentActivity;

import es.ulpgc.alexmoreno.beacons.app.AppMediator;

public class ProfileScreen {

    public static void configure(ProfileContract.View view) {

        WeakReference<FragmentActivity> context =
                new WeakReference<>((FragmentActivity) view);

        AppMediator mediator = (AppMediator) context.get().getApplication();
        ProfileState state = mediator.getProfileState();

        ProfileContract.Router router = new ProfileRouter(mediator);
        ProfileContract.Presenter presenter = new ProfilePresenter(state);
        ProfileContract.Model model = new ProfileModel();
        presenter.injectModel(model);
        presenter.injectRouter(router);
        presenter.injectView(new WeakReference<>(view));

        view.injectPresenter(presenter);

    }
}
