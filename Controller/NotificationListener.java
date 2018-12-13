package Controller;
import java.rmi.MarshalledObject;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.Timer;
import net.jini.core.event.*;
import res.*;

public class NotificationListener implements RemoteEventListener
{
    private SpaceController spaceController;

    public NotificationListener()
    {
        spaceController = new SpaceController();
    }

    public void notify(RemoteEvent ev)
    {
        new Thread() {
            public void run() {

                String notificationText = "";

                try {
                    notificationText = "New message for " + ev.getRegistrationObject().get();
                    WPTopic topic = new WPTopic();
                    topic.title = (String)ev.getRegistrationObject().get();

                    if (null == spaceController.readTopic(topic)) {
                        notificationText = "Topic: " + ev.getRegistrationObject().get() + " has been deleted";
                    }
                } catch (Exception e) {
                    //Do nothing
                    e.printStackTrace();
                }

                try{
                    final JOptionPane optionPane = new JOptionPane(
                            notificationText,
                            JOptionPane.INFORMATION_MESSAGE,
                            JOptionPane.DEFAULT_OPTION,
                            null,
                            new Object[]{},
                            null);

                    //TODO create and use own jdialog frame
                    JFrame f = new JFrame();
                    final JDialog dialog = new JDialog(f, "Notification!");
                    dialog.setContentPane(optionPane);
                    dialog.pack();
                    Timer timer = new Timer(2000, new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            dialog.setVisible(false);
                            dialog.dispose();
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                    dialog.setVisible(true); // if modal, application will pause here
                } catch (Exception e) {
                    //DO nothing
                }

            }
        }.start();
    }
}
