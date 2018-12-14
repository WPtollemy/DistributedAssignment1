package Controller;
import View.Login;
import res.WPUser;

public class Main
{
    //To remember which user is logged in
    static WPUser loggedUser;

    public static void main(String[] args) {
        new Login().setVisible(true);
    }

    public static void logUserIn(WPUser user)
    {
        Main.loggedUser = user;
    }

    public static WPUser getLoggedUser()
    {
        return Main.loggedUser;
    }
}
