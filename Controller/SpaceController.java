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
    private static int THREE_MINUTES      = 3000 * 60;
    private static final long TWO_SECONDS = 2 * 1000;
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
        Transaction.Created trc = null;
        try {
            trc = TransactionFactory.create(mgr, 3000);
        } catch (Exception e) {
            System.out.println("Could not create transaction " + e);;
        }

        Transaction txn = trc.transaction; 

        WPUser existingUser = null;
        try {
            existingUser = (WPUser)space.readIfExists(user, txn, TWO_SECONDS);
        }  catch ( Exception e) {
            e.printStackTrace();
        }

        if (existingUser == null){
            try {
                space.write( user, txn, THREE_MINUTES);
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
            WPUser loggedUser = (WPUser)space.readIfExists( user, null, TWO_SECONDS);
            return loggedUser;
        }  catch ( Exception e) {
            e.printStackTrace();
        }

        return user = new WPUser();
    }

    public WPTopic readTopic(WPTopic topic)
    {
        try {
            WPTopic foundTopic = (WPTopic)space.readIfExists(topic,null, TWO_SECONDS);

            return foundTopic;
        }  catch ( Exception e) {
            e.printStackTrace();
        }

        return topic = new WPTopic();
    }

    public ArrayList<String> getTopicList()
    {
        ArrayList<WPTopic> topicsTpl = new ArrayList<WPTopic>();
        WPTopic topicTpl             = new WPTopic();
        topicsTpl.add(topicTpl);

        MatchSet set = null;
        Entry entry = null;

        ArrayList<String> topicTitles = new ArrayList<String>();
        WPTopic result;

        try {
            set = space.contents(topicsTpl, null, 500, 1000);
            while (null != set) {
                entry = set.next();
                if (null == entry) {
                    break;
                } else {
                    result = (WPTopic)entry;
                    topicTitles.add(result.title);
                }
            } 
        } catch (Exception e) {
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
        Transaction.Created trc = null;
        try {
            trc = TransactionFactory.create(mgr, 3000);
        } catch (Exception e) {
            System.out.println("Could not create transaction " + e);;
        }

        Transaction txn = trc.transaction; 

        WPUser existingUser = null;
        try {
            existingUser = (WPUser)space.take(user, txn, TWO_SECONDS);
        }  catch ( Exception e) {
            e.printStackTrace();
        }

        existingUser.subscribe(topic);

        try {
            space.write( user, txn, THREE_MINUTES);
        }  catch ( Exception e) {
            e.printStackTrace();
        }

        try {
            txn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
