package Controller;
import java.util.ArrayList;
import net.jini.core.lease.*;
import net.jini.core.transaction.*;
import net.jini.core.transaction.server.*;
import net.jini.space.*;
import res.*;

public class SpaceController
{
    private static int THREE_MINUTES      = 3000 * 60;
    private static final long TWO_SECONDS = 2 * 1000;  // two thousand milliseconds
    private JavaSpace05 space;
    private TransactionManager mgr;

    public SpaceController()
    {
        space = SpaceUtils.getSpace();
        if (space == null){
            System.err.println("Failed to find the javaspace");
            System.exit(1);
        }

        mgr = SpaceUtils.getManager();
        if (mgr == null){
            System.err.println("Failed to find the transaction manager");
            System.exit(1);
        }
    }

    public void writeTopic(WPTopic topic)
    {
        try {
            space.write( topic, null, THREE_MINUTES);
        }  catch ( Exception e) {
            e.printStackTrace();
        }
    }

    public void writeUser(WPUser user)
    {
        try {
            space.write( user, null, THREE_MINUTES);
        }  catch ( Exception e) {
            e.printStackTrace();
        }
    }

    public WPTopic readTopic(WPTopic topic)
    {
        try {
            WPTopic nextJob = (WPTopic)space.readIfExists(topic,null, TWO_SECONDS);

            return nextJob;
        }  catch ( Exception e) {
            e.printStackTrace();
        }

        return topic = new WPTopic();
    }

    public ArrayList<String> getTopicList()
    {
        Transaction.Created trc = null;
        try {
             trc = TransactionFactory.create(mgr, 3000);
        } catch (Exception e) {
             System.out.println("Could not create transaction " + e);;
        }

        Transaction txn = trc.transaction; 

        WPTopic topicTpl = new WPTopic();
        ArrayList<WPTopic> topicsTpl = new ArrayList<WPTopic>();
        topicsTpl.add(topicTpl);

        ArrayList<WPTopic> topicList = new ArrayList<WPTopic>();
        topicList.addAll(space.take(topicsTpl, txn, TWO_SECONDS, 50));

        ArrayList<String> topicTitles = new ArrayList<String>();
        for (WPTopic topic : topicList) {
            topicTitles.add(topic.title);
        }

        return topicTitles;
    }

    public boolean doesUserExist(WPUser user)
    {
        try {
            WPUser existingUser = (WPUser)space.readIfExists(user, null, TWO_SECONDS);

            if (existingUser != null)
                return true;
            
        }  catch ( Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void writeMessage(WPMessage message)
    {
        try {
            space.write( message, null, THREE_MINUTES);
        }  catch ( Exception e) {
            e.printStackTrace();
        }
    }

    public WPMessage getMessage(WPMessage message)
    {
        try {
            WPMessage exMessage = (WPMessage)space.readIfExists(message,null, TWO_SECONDS);

            return exMessage;
        }  catch ( Exception e) {
            e.printStackTrace();
        }

        return message = new WPMessage();
    }
}
