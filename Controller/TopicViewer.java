package Controller;
import java.rmi.MarshalledObject;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.Timer;
import net.jini.core.event.*;
import net.jini.export.Exporter;
import net.jini.jeri.BasicILFactory;
import net.jini.jeri.BasicJeriExporter;
import net.jini.jeri.tcp.TcpServerEndpoint;
import net.jini.space.JavaSpace;
import res.WPMessage;
import res.WPTopic;

public class TopicViewer
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
        WPTopic template = new WPTopic();
        template.title   = topicTitle;

        WPTopic topic = spaceController.readTopic(template);

        return topic;
    }

    public String findTopicOwner(String topicTitle)
    {
        WPTopic topic = findTopic(topicTitle.trim());
        return topic.topicOwner;
    }

    public ArrayList<String> getMessageList(String topicTitle)
    {
        WPTopic selectedTopic = findTopic(topicTitle.trim());
        ArrayList<WPMessage> messageList = spaceController.getMessageList(topicTitle.trim());
        ArrayList<String> messageTitles = new ArrayList<String>();

        try {
            for (WPMessage message : messageList) {
                //Ensure the logged in user doesn't see all messages
                boolean messageOwnerIsLoggedUser = message.messageOwner.equals(Main.getLoggedUser().name);
                boolean topicOwnerIsLoggedUser   = selectedTopic.topicOwner.equals(Main.getLoggedUser().name);
                if (message.isPrivate && 
                        !messageOwnerIsLoggedUser &&
                        !topicOwnerIsLoggedUser
                   ){
                    continue;
                   }

                messageTitles.add(message.messageOwner + ": " + message.message);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        //TODO subscribe user to topic
        // Creates the notification for user        
        // create the exporter
        Exporter myDefaultExporter =
            new BasicJeriExporter(TcpServerEndpoint.getInstance(0),
                    new BasicILFactory(), false, true);

        NotificationListener listener = new NotificationListener();

        try {
            // register this as a remote object
            // and get a reference to the 'stub'
            theStub = (RemoteEventListener) myDefaultExporter.export(listener);

            // add the listener
            WPMessage template = new WPMessage();
            template.topic     = topic;
            template.isPrivate = false;
            space.notify(template, null, this.theStub, FIFTEEN_MINUTES, new MarshalledObject(topic));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
