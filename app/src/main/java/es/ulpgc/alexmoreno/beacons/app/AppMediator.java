package es.ulpgc.alexmoreno.beacons.app;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;

import es.ulpgc.alexmoreno.beacons.beaconsFinder.BeaconsFinderState;
import es.ulpgc.alexmoreno.beacons.data.MasterItem;
import es.ulpgc.alexmoreno.beacons.detail.DetailState;
import es.ulpgc.alexmoreno.beacons.forgotten.ForgottenState;
import es.ulpgc.alexmoreno.beacons.login.LoginState;
import es.ulpgc.alexmoreno.beacons.master.MasterState;
import es.ulpgc.alexmoreno.beacons.profile.ProfileState;
import es.ulpgc.alexmoreno.beacons.register.RegisterState;

public class AppMediator extends Application {
    // states
    private BeaconsFinderState beaconsFinderState;
    private DetailState detailState;
    private ForgottenState forgottenState;
    private LoginState loginState;
    private MasterState masterState;
    private ProfileState profileState;
    private RegisterState registerState;

    // items to save in mediator
    private MasterItem masterItem;
    private String placeUUID;


    @Override
    public void onCreate() {
        super.onCreate();

        beaconsFinderState = new BeaconsFinderState();
        detailState = new DetailState();
        forgottenState = new ForgottenState();
        loginState = new LoginState();
        masterState = new MasterState();
        profileState = new ProfileState();
        registerState = new RegisterState();
    }

    public void setBeaconsFinderState(BeaconsFinderState beaconsFinderState) {
        this.beaconsFinderState = beaconsFinderState;
    }

    public BeaconsFinderState getBeaconsFinderState() {
        return beaconsFinderState;
    }

    public DetailState getDetailState() {
        return detailState;
    }

    public void setDetailState(DetailState detailState) {
        this.detailState = detailState;
    }

    public ForgottenState getForgottenState() {
        return forgottenState;
    }

    public void setForgottenState(ForgottenState forgottenState) {
        this.forgottenState = forgottenState;
    }

    public LoginState getLoginState() {
        return loginState;
    }

    public void setLoginState(LoginState loginState) {
        this.loginState = loginState;
    }

    public MasterState getMasterState() {
        return masterState;
    }

    public void setMasterState(MasterState masterState) {
        this.masterState = masterState;
    }

    public ProfileState getProfileState() {
        return profileState;
    }

    public void setProfileState(ProfileState profileState) {
        this.profileState = profileState;
    }

    public RegisterState getRegisterState() {
        return registerState;
    }

    public void setRegisterState(RegisterState registerState) {
        this.registerState = registerState;
    }

    public void setMasterItem(MasterItem item) {
        this.masterItem = item;
    }

    public MasterItem getMasterItem() {
        return masterItem;
    }

    public String getPlaceUUID() {
        return placeUUID;
    }

    public void setPlaceUUID(String placeUUID) {
        this.placeUUID = placeUUID;
    }
}
