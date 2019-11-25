package es.ulpgc.alexmoreno.beacons.forgotten;

import es.ulpgc.alexmoreno.beacons.app.RepositoryInterface;

public class ForgottenModel implements ForgottenContract.Model {

    public static String TAG = ForgottenModel.class.getSimpleName();
    private RepositoryInterface repository;

    public ForgottenModel(RepositoryInterface repository) {
        this.repository = repository;
    }

    @Override
    public void sendNewPassword(String userMail, RepositoryInterface.NewPasswordInterface callback) {
        repository.sendNewPassword(userMail, callback);
    }
}
