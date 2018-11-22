package Controller;
import View.Lobby;
import res.WPUser;

public class Register 
{
    private SpaceController spaceController = new SpaceController();

    public Register()
    {
    }

    public boolean validUser(String username, String password)
    {
        //Add password validation etc

        WPUser user = new WPUser(username, password);
        return !spaceController.doesUserExist(user);    
    }

    public void registerUser(String username, String password)
    {
        WPUser user = new WPUser(username, password);

        if (this.validUser(username, password))
            spaceController.writeUser(user);

        return;
    }

    public void loginUser()
    {
        Lobby lobby = new Lobby(); 
        lobby.setVisible(true);
    }
}
