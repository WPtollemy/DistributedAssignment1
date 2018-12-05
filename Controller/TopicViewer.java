package Controller;
import java.util.ArrayList;
import res.WPMessage;
import res.WPTopic;

public class TopicViewer
{
    private SpaceController spaceController;

    public TopicViewer()
    {
        spaceController = new SpaceController();
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
                    message.messageOwner != Main.getLoggedUser().name &&
                    selectedTopic.topicOwner != Main.getLoggedUser().name
               ){
                continue;
               }

            messageTitles.add(message.messageOwner + ": " + message.message);
        }

        return messageTitles;
    }

    public void addMessage(String topicTitle, String content)
    {
        WPMessage message    = new WPMessage();
        message.topic        = topicTitle;
        message.message      = content;
        message.messageOwner = Main.getLoggedUser().name;

        spaceController.writeMessage(message);
    }
}
