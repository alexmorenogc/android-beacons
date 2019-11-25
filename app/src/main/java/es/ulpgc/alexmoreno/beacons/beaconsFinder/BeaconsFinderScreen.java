package es.ulpgc.alexmoreno.beacons.beaconsFinder;

import androidx.fragment.app.FragmentActivity;

import java.lang.ref.WeakReference;

import es.ulpgc.alexmoreno.beacons.app.AppMediator;
import es.ulpgc.alexmoreno.beacons.app.Repository;
import es.ulpgc.alexmoreno.beacons.app.RepositoryInterface;

public class BeaconsFinderScreen {

    public static void configure(BeaconsFinderContract.View view) {

        WeakReference<FragmentActivity> context =
                new WeakReference<>((FragmentActivity) view);

        AppMediator mediator = (AppMediator) context.get().getApplication();
        BeaconsFinderState state = mediator.getBeaconsFinderState();
        RepositoryInterface repository = Repository.getInstance(context.get());


        BeaconsFinderContract.Router router = new BeaconsFinderRouter(mediator);
        BeaconsFinderContract.Presenter presenter = new BeaconsFinderPresenter(state);
        BeaconsFinderContract.Model model = new BeaconsFinderModel(repository);
        presenter.injectModel(model);
        presenter.injectRouter(router);
        presenter.injectView(new WeakReference<>(view));

        view.injectPresenter(presenter);

    }
}
