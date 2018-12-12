package Controller;
import View.TopicLister;
import View.Register;
import res.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        TopicLister lobby = new TopicLister(); 
        lobby.setVisible(true);
    }

    public void registerUser()
    {
        Register register = Register.getInstance();
        register.setVisible(true);
    }
}
