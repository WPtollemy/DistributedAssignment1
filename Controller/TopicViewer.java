package Controller;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.Timer;
import net.jini.core.event.*;
import net.jini.core.lease.Lease;
import net.jini.export.Exporter;
import net.jini.jeri.BasicILFactory;
import net.jini.jeri.BasicJeriExporter;
import net.jini.jeri.tcp.TcpServerEndpoint;
import net.jini.space.JavaSpace;
import res.WPMessage;
import res.WPTopic;

public class TopicViewer implements RemoteEventListener
{
    //Space vars
    private JavaSpace space;
    private RemoteEventListener theStub;
    private SpaceController spaceController;

    //Other vars
    private static int FIFTEEN_MINUTES = 15000 * 60;

    public TopicViewer()
    {
        spaceController = new SpaceController();
        // find the space
        space = Controller.SpaceUtils.getSpace();
        if (space == null){
            System.err.println("Failed to find the javaspace");
            System.exit(1);
        }
    }

    public WPTopic findTopic(String topicTitle)
    {
        WPTopic testing = new WPTopic();
        testing.title   = topicTitle;

        WPTopic topic = spaceController.readTopic(testing);

        return topic;
    }

    public String findTopicMessage(WPTopic topic)
    {
        WPMessage message = new WPMessage();
        message.topic     = topic.title;

        WPMessage newMessage = spaceController.getMessage(message);

        if (null == newMessage)
            return "";

        return newMessage.messageOwner + ": " + newMessage.message; 
    }

    public ArrayList<String> getTopicList()
    {
        return spaceController.getTopicList();
    }

    public ArrayList<String> getMessageList(String topicTitle)
    {
        WPTopic selectedTopic = this.findTopic(topicTitle);
        ArrayList<WPMessage> messageList = spaceController.getMessageList(topicTitle);

        ArrayList<String> messageTitles = new ArrayList<String>();
        for (WPMessage message : messageList) {
            //Ensure the logged in user doesn't see all messages
            if (message.isPrivate && 
                    (!message.messageOwner.equals(Main.getLoggedUser().name) &&
                    !selectedTopic.topicOwner.equals(Main.getLoggedUser().name))
               ){
                continue;
               }

            messageTitles.add(message.messageOwner + ": " + message.message);
        }

        return messageTitles;
    }

    public void addMessage(String topicTitle, String content, Boolean isPrivate)
    {
        WPMessage message    = new WPMessage();
        message.topic        = topicTitle;
        message.message      = content;
        message.messageOwner = Main.getLoggedUser().name;
        message.isPrivate    = isPrivate;

        spaceController.writeMessage(message);
    }

    public void subscribeToTopic(String topic)
    {
        spaceController.subscribeUserToTopic(Main.getLoggedUser(), topic);
        createNotification(topic);
    }

    public void createNotification(String topic)
    {
        // create the exporter
        Exporter myDefaultExporter =
            new BasicJeriExporter(TcpServerEndpoint.getInstance(0),
                    new BasicILFactory(), false, true);

        try {
            // register this as a remote object
            // and get a reference to the 'stub'
            theStub = (RemoteEventListener) myDefaultExporter.export(this);

            // add the listener
            WPMessage template = new WPMessage();
            template.topic = topic;
            space.notify(template, null, this.theStub, FIFTEEN_MINUTES, null);
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
