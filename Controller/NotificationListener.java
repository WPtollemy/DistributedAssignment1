package Controller;
import java.rmi.MarshalledObject;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.Timer;
import net.jini.core.event.*;

public class NotificationListener implements RemoteEventListener
{
    public NotificationListener()
    {
    }

    public void notify(RemoteEvent ev)
    {
        //Create notification
        System.out.println("Notif works");

        new Thread() {
            public void run() {

                try{
                    final JOptionPane optionPane = new JOptionPane(
                            "New Notification for " + ev.getRegistrationObject().get(),
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
