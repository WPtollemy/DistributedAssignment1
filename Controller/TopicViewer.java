package Controller;
import Controller.Main;
import Controller.SpaceController;
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
        testing         = testing;

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

   public void addMessage(String topicTitle, String content)
   {
       WPMessage message    = new WPMessage();
       message.topic        = topicTitle;
       message.message      = content;
       message.messageOwner = Main.getLoggedUser().name;

       spaceController.writeMessage(message);
   }
}
