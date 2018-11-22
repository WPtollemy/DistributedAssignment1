package Controller;
import res.WPUser;
import View.Login;

public class Main
{
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
