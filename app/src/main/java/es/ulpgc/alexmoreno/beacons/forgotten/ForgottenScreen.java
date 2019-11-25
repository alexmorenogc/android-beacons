package es.ulpgc.alexmoreno.beacons.forgotten;

import java.lang.ref.WeakReference;
import androidx.fragment.app.FragmentActivity;
import es.ulpgc.alexmoreno.beacons.app.AppMediator;
import es.ulpgc.alexmoreno.beacons.app.Repository;
import es.ulpgc.alexmoreno.beacons.app.RepositoryInterface;

public class ForgottenScreen {
    public static void configure(ForgottenContract.View view) {

        WeakReference<FragmentActivity> context =
                new WeakReference<>((FragmentActivity) view);

        AppMediator mediator = (AppMediator) context.get().getApplication();
        ForgottenState state = mediator.getForgottenState();

        RepositoryInterface repository = Repository.getInstance(context.get());


        ForgottenContract.Router router = new ForgottenRouter(mediator);
        ForgottenContract.Presenter presenter = new ForgottenPresenter(state);
        ForgottenContract.Model model = new ForgottenModel(repository);
        presenter.injectModel(model);
        presenter.injectRouter(router);
        presenter.injectView(new WeakReference<>(view));

        view.injectPresenter(presenter);
    }
}
