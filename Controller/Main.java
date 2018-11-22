package Controller;
import res.WPUser;
import View.Login;
import res.Lookup.Panels;

public class Main
{
    static WPUser loggedUser;
    static JFrame mainFrame;

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

    public static void changeScreen(int screenId)
    {
        switch (screenId) {
            case Panels.LOGIN_ID:
                //Swap in login panel
                break;
            case Panels.REGISTER_ID:
                //Swap in login panel
                break;
            case Panels.LOBBY_ID:
                //Swap in login panel
                break;
            case Panels.TOPIC_CREATE_ID:
                //Swap in login panel
                break;
            case Panels.TOPIC_VIEW_ID:
                //Swap in login panel
                break;
            case Panels.TOPIC_OWNED_ID:
                //Swap in login panel
                break;
        }
    }
}
