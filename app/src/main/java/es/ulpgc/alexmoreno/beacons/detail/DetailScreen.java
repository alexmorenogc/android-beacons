package es.ulpgc.alexmoreno.beacons.detail;

import androidx.fragment.app.FragmentActivity;
import java.lang.ref.WeakReference;
import es.ulpgc.alexmoreno.beacons.app.AppMediator;
import es.ulpgc.alexmoreno.beacons.app.Repository;
import es.ulpgc.alexmoreno.beacons.app.RepositoryInterface;

public class DetailScreen {

    public static void configure(DetailContract.View view) {

        WeakReference<FragmentActivity> context =
                new WeakReference<>((FragmentActivity) view);

        AppMediator mediator = (AppMediator) context.get().getApplication();
        DetailState state = mediator.getDetailState();

        RepositoryInterface repository = Repository.getInstance(context.get());

        DetailContract.Router router = new DetailRouter(mediator);
        DetailContract.Presenter presenter = new DetailPresenter(state);
        DetailContract.Model model = new DetailModel(repository);
        presenter.injectModel(model);
        presenter.injectRouter(router);
        presenter.injectView(new WeakReference<>(view));

        view.injectPresenter(presenter);

    }
}
