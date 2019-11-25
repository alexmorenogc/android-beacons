package es.ulpgc.alexmoreno.beacons.register;

import androidx.fragment.app.FragmentActivity;

import java.lang.ref.WeakReference;

import es.ulpgc.alexmoreno.beacons.app.AppMediator;
import es.ulpgc.alexmoreno.beacons.app.Repository;
import es.ulpgc.alexmoreno.beacons.app.RepositoryInterface;

public class RegisterScreen {

    public static void configure(RegisterContract.View view) {

        WeakReference<FragmentActivity> context =
                new WeakReference<>((FragmentActivity) view);

        AppMediator mediator = (AppMediator) context.get().getApplication();
        RegisterState state = mediator.getRegisterState();
        RepositoryInterface repository = Repository.getInstance(context.get());


        RegisterContract.Router router = new RegisterRouter(mediator);
        RegisterContract.Presenter presenter = new RegisterPresenter(state);
        RegisterContract.Model model = new RegisterModel(repository);
        presenter.injectModel(model);
        presenter.injectRouter(router);
        presenter.injectView(new WeakReference<>(view));

        view.injectPresenter(presenter);

    }
}
