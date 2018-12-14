package Controller;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.MarshalledObject;
import javax.swing.*;
import javax.swing.Timer;
import net.jini.core.event.*;
import res.*;

/*
 * Listener for general notifications/subscriptions
 * so only this needs to be used when subscribing a user
 * to set up the notification.
 * Potential to store notifications and use this on log in
 */
public class NotificationListener implements RemoteEventListener
{
    private SpaceController spaceController;

    public NotificationListener()
    {
        spaceController = new SpaceController();
    }

    //General notify event for all notifications
    public void notify(RemoteEvent ev)
    {
        //Start notification window in thread so doesn't interrupt user
        //and multiple notifications can appear
        new Thread() {
            public void run() {

                String notificationText = "";

                //Determine whether a topic exists, to decide to notification text
                try {
                    notificationText = "New message for " + ev.getRegistrationObject().get();
                    WPTopic topic = new WPTopic();
                    topic.title = (String)ev.getRegistrationObject().get();

                    //If the topic doesn't exist override the text to say it has been deleted
                    if (null == spaceController.readTopic(topic)) {
                        notificationText = "Topic: " + ev.getRegistrationObject().get() + " has been deleted";
                    }
                } catch (Exception e) {
                    //Do nothing
                    e.printStackTrace();
                }

                //Create a simple small window containing the notification
                try{
                    final JOptionPane optionPane = new JOptionPane(
                            notificationText,
                            JOptionPane.INFORMATION_MESSAGE,
                            JOptionPane.DEFAULT_OPTION,
                            null,
                            new Object[]{},
                            null);

                    JFrame f = new JFrame();
                    final JDialog dialog = new JDialog(f, "Notification!");
                    dialog.setContentPane(optionPane);
                    dialog.pack();
                    //Ensure the notification only displays for a few seconds
                    Timer timer = new Timer(2000, new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            dialog.setVisible(false);
                            dialog.dispose();
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                    dialog.setVisible(true);
                } catch (Exception e) {
                    //Do nothing in current case because
                    //missing a notification shouldn't stop the program
                }

            }
        }.start();
    }
}
