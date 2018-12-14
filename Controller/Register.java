package Controller;
import res.WPUser;

public class Register 
{
    private SpaceController spaceController = new SpaceController();

    public Register()
    {
    }

    //Validates details entered
    public boolean validUser(String username, String password)
    {
        //Potential to add password validation e.g. min 11 characters etc 

        //Check if the user already exists, invalid if one is
        WPUser user = new WPUser(username, password);
        return !spaceController.doesUserExist(user);    
    }

    //Create user and write to space
    public void registerUser(String username, String password)
    {
        WPUser user = new WPUser(username, password);

        // Check the user doesn't exist before creating one
        if (this.validUser(username, password))
            spaceController.writeUser(user);

        return;
    }
}
