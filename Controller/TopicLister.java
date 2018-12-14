package Controller;
import View.TopicCreator;
import View.TopicViewer;
import java.util.ArrayList;
import res.WPTopic;

public class TopicLister
{
    private SpaceController spaceController = new SpaceController();

    public TopicLister()
    {
    }

    //Show the topic creator window
    public void createTopic()
    {
        TopicCreator topicCreator = TopicCreator.getInstance();
        topicCreator.setVisible(true);
    }

    //Show the topic viewer / message viewer window
    public void viewTopic(String selectedTopic)
    {
        TopicViewer topicViewer = new TopicViewer(selectedTopic);
    }

    public ArrayList<String> getTopicList(boolean userTopicsOnly)
    {
        ArrayList<String> topicsTitles = new ArrayList<String>();

        //Get every topic in the space
        for (WPTopic topic : spaceController.getTopicList()) {
            //If user selects only their topics, ensure they are the topic owner
            //for each topic found
            if (!userTopicsOnly ||
                    topic.topicOwner.equals(Main.getLoggedUser().name))
                topicsTitles.add(topic.title);
        }

        return topicsTitles;
    }

    //Handle when user presses to delete the topic
    public void deleteTopic(String topicTitle)
    {
        WPTopic template = new WPTopic();
        template.title = topicTitle;

        spaceController.deleteTopic(template);
    }
}
