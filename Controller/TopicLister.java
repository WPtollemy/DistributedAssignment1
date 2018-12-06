package Controller;
import java.util.ArrayList;

public class TopicLister
{
    private SpaceController spaceController = new SpaceController();

    public TopicLister()
    {
    }

    public ArrayList<String> getTopicList()
    {
        return spaceController.getTopicList();
    }
}
