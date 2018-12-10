package Controller;
import View.TopicLister;
import View.Register;
import res.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.jini.core.event.*;
import net.jini.core.lease.Lease;
import net.jini.export.Exporter;
import net.jini.jeri.BasicILFactory;
import net.jini.jeri.BasicJeriExporter;
import net.jini.jeri.tcp.TcpServerEndpoint;
import net.jini.space.JavaSpace;

public class Login implements RemoteEventListener 
{
    //Space vars
    private JavaSpace space;
    private RemoteEventListener theStub;
    private SpaceController spaceController = new SpaceController();

    //Other vars
    private static int FIFTEEN_MINUTES = 15000 * 60;

    public Login()
    {
        // find the space
        space = Controller.SpaceUtils.getSpace();
        if (space == null){
            System.err.println("Failed to find the javaspace");
            System.exit(1);
        }
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
        setupNotification(toLogUser);

        TopicLister lobby = new TopicLister(); 
        lobby.setVisible(true);
    }

    public void registerUser()
    {
        Register register = Register.getInstance();
        register.setVisible(true);
    }

    private void setupNotification(WPUser user)
    {
        // create the exporter
        Exporter myDefaultExporter =
            new BasicJeriExporter(TcpServerEndpoint.getInstance(0),
                    new BasicILFactory(), false, true);

        try {
            // register this as a remote object
            // and get a reference to the 'stub'
            theStub = (RemoteEventListener) myDefaultExporter.export(this);

            for (String topic : user.subscribedTopics) {
                // add the listener
                WPMessage template = new WPMessage();
                template.topic = topic;
                space.notify(template, null, this.theStub, FIFTEEN_MINUTES, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // this is the method called when we are notified
    // of an object of interest
    public void notify(RemoteEvent ev)
    {
        //TODO create and use own jdialog frame
        JFrame f = new JFrame();
        final JDialog dialog = new JDialog(f, "Test", true);
        dialog.setMinimumSize(new Dimension(100,100));
        Timer timer = new Timer(2000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
                dialog.dispose();
            }
        });
        timer.setRepeats(false);
        timer.start();

        dialog.setVisible(true); // if modal, application will pause here
    }
}
