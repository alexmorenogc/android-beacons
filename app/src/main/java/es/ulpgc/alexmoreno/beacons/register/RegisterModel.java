package es.ulpgc.alexmoreno.beacons.register;

import android.util.Log;

import es.ulpgc.alexmoreno.beacons.app.RepositoryInterface;
import es.ulpgc.alexmoreno.beacons.data.LoginUser;

public class RegisterModel implements RegisterContract.Model {

    public static String TAG = RegisterModel.class.getSimpleName();
    private RepositoryInterface repository;

    public RegisterModel(RepositoryInterface repository) {
        this.repository = repository;
    }


    @Override
    public void registerNewUser(LoginUser user, RepositoryInterface.RegisterNewUser callback) {
        Log.d(TAG, "registerNewUser: " + user.toString());
        repository.registerNewUser(user,callback);
    }
}
