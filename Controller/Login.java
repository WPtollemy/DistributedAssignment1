package Controller;
import View.Lobby;
import View.Register;
import res.WPUser;

public class Login 
{
    private SpaceController spaceController = new SpaceController();

    public Login()
    {
    }

    public boolean validUser(String username, String password)
    {
        WPUser user = new WPUser(username, password);
        //Just check if it's in the space for now
        return spaceController.doesUserExist(user);    
    }

    public void loginUser(String username, String password)
    {
        WPUser user = new WPUser(username, password);

        if (!validUser(username, password)){
            return;
        }

        WPUser toLogUser = spaceController.readUser(user);
        Main.logUserIn(toLogUser);

        Lobby lobby = new Lobby(); 
        lobby.setVisible(true);
    }

    public void registerUser()
    {
        Register register = Register.getInstance();
        register.setVisible(true);
    }
}
