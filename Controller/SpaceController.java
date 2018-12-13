package Controller;
import java.util.ArrayList;
import net.jini.core.lease.*;
import net.jini.core.transaction.*;
import net.jini.core.transaction.server.*;
import net.jini.space.*;
import res.*;
import net.jini.core.entry.Entry;

public class SpaceController
{
    private static int WRITE_TIME       = 3000 * 60;
    private static final long READ_TIME = 1 * 1000;
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
            space.write( topic, null, WRITE_TIME);
        }  catch ( Exception e) {
            e.printStackTrace();
        }
    }

    public void writeUser(WPUser user)
    {
        Transaction.Created trc = null;
        try {
            trc = TransactionFactory.create(mgr, 3000);
        } catch (Exception e) {
            System.out.println("Could not create transaction " + e);;
        }

        Transaction txn = trc.transaction; 

        WPUser existingUser = null;
        try {
            existingUser = (WPUser)space.readIfExists(user, txn, READ_TIME);
        }  catch ( Exception e) {
            e.printStackTrace();
        }

        if (existingUser == null){
            try {
                space.write( user, txn, WRITE_TIME);
            }  catch ( Exception e) {
                e.printStackTrace();
            }
        }

        try {
            txn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public WPUser readUser(WPUser user)
    {
        try {
            WPUser loggedUser = (WPUser)space.readIfExists( user, null, READ_TIME);
            return loggedUser;
        }  catch ( Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public WPTopic readTopic(WPTopic topic)
    {
        try {
            WPTopic foundTopic = (WPTopic)space.readIfExists(topic,null, READ_TIME);

            return foundTopic;
        }  catch ( Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void deleteTopic(WPTopic topic)
    {
        Transaction.Created trc = null;
        try {
            trc = TransactionFactory.create(mgr, 3000);
        } catch (Exception e) {
            System.out.println("Could not create transaction " + e);;
        }

        Transaction txn = trc.transaction; 

        try {
            WPMessage foundMessage = new WPMessage();

            do {
                WPMessage message      = new WPMessage();
                message.topic          = topic.title;
                foundMessage = (WPMessage)space.take( message, null, READ_TIME);
            } while (null != foundMessage);

            space.take( topic, txn, READ_TIME);
        }  catch ( Exception e) {
            e.printStackTrace();
        }

        try {
            txn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            WPMessage message = new WPMessage();
            message.topic     = topic.title;
            message.isPrivate = false;
            space.write( message, null, 1000); //Write a temporary message to trigger a notification
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<WPTopic> getTopicList()
    {
        ArrayList<WPTopic> topicsTpl = new ArrayList<WPTopic>();
        WPTopic topicTpl             = new WPTopic();
        topicsTpl.add(topicTpl);

        MatchSet set = null;
        Entry entry = null;

        ArrayList<WPTopic> topics = new ArrayList<WPTopic>();

        try {
            set = space.contents(topicsTpl, null, 500, 1000);
            while (null != set) {
                entry = set.next();
                if (null == entry) {
                    break;
                } else {
                    topics.add((WPTopic)entry);
                }
            } 
        } catch (Exception e) {
        }

        return topics;
    }

    public boolean doesUserExist(WPUser user)
    {
        try {
            WPUser existingUser = (WPUser)space.readIfExists(user, null, READ_TIME);

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
            space.write( message, null, WRITE_TIME);
        }  catch ( Exception e) {
            e.printStackTrace();
        }
    }

    public WPMessage getMessage(WPMessage message)
    {
        try {
            WPMessage exMessage = (WPMessage)space.readIfExists(message,null, READ_TIME);

            return exMessage;
        }  catch ( Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<WPMessage> getMessageList(String topicTitle)
    {
        ArrayList<WPMessage> messagesTpl = new ArrayList<WPMessage>();
        WPMessage messageTpl             = new WPMessage();
        messageTpl.topic = topicTitle;
        messagesTpl.add(messageTpl);

        MatchSet set = null;
        Entry entry = null;
        
        ArrayList<WPMessage> messages = new ArrayList<WPMessage>();

        try {
            set = space.contents(messagesTpl, null, 500, 1000);
            while (null != set) {
                entry = set.next();
                if (null == entry) {
                    break;
                } else {
                    messages.add((WPMessage)entry);
                }
            } 
        } catch (Exception e) {
        }

        return messages;
    }

    public void subscribeUserToTopic(WPUser user, String topic)
    {
        //TODO create subscription object
    }
}
