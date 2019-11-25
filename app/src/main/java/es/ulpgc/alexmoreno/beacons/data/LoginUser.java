package es.ulpgc.alexmoreno.beacons.data;

/**
 * Class to set and get the user from Firebase
 */
public class LoginUser {

    private String email;
    private String password;
    private String name;
    private String surname;

    public LoginUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginUser(String name, String surname, String email, String password) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}