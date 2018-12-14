package Controller;
import View.Register;
import View.TopicLister;
import res.*;

public class Login 
{
    private SpaceController spaceController = new SpaceController();

    public Login()
    {
    }

    //Check if the user exists (and potential for other validation)
    public boolean validUser(String username, String password)
    {
        WPUser user = new WPUser(username, password);
        //Just check if it's in the space
        return spaceController.doesUserExist(user);    
    }

    public void loginUser(String username, String password)
    {
        WPUser user = new WPUser(username, password);

        //If it isn't a valid user stop the log in
        if (!validUser(username, password)){
            return;
        }

        //Get the user that's logging in and ensure they are remembered
        WPUser toLogUser = spaceController.readUser(user);
        Main.logUserIn(toLogUser);

        TopicLister lobby = new TopicLister(); 
        lobby.setVisible(true);
    }

    public void registerUser()
    {
        //Load the register form
        Register register = Register.getInstance();
        register.setVisible(true);
    }
}
