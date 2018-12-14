package tests.Controller;
import junit.framework.*;
import org.junit.Test;
import Controller.TopicLister;
import Controller.SpaceController;
import View.TopicCreator;
import res.*;

public class TopicListerControllerTest extends TestCase
{
    private TopicLister topicListerController;
    private SpaceController spaceController = new SpaceController();

    protected void setUp()
    {
        topicListerController = new TopicLister();
    }

    @Test
    public void testTopicCreateLoadsCreationForm()
    {
        topicListerController.createTopic();

        TopicCreator topicCreator = TopicCreator.getInstance();
        assertTrue(topicCreator.isShowing());
    }

    @Test
    public void testTopicGetsDeleted()
    {
        WPTopic template = new WPTopic();
        template.title = "test topic deletion";
        spaceController.writeTopic(template);

        topicListerController.deleteTopic("test topic deletion");

        assertNull(spaceController.readTopic(template));
    }
}
