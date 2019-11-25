package es.ulpgc.alexmoreno.beacons.login;

import es.ulpgc.alexmoreno.beacons.app.RepositoryInterface;
import es.ulpgc.alexmoreno.beacons.data.LoginUser;

public class LoginModel implements LoginContract.Model {

    public static String TAG = LoginModel.class.getSimpleName();

    private RepositoryInterface repository;

    public LoginModel(RepositoryInterface repository) {
        this.repository = repository;
    }

    @Override
    public void loginUser(LoginUser user, RepositoryInterface.LoginUserInterface callback) {
        repository.logInUser(user, callback);
    }

}
