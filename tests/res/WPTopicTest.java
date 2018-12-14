package tests.res;
import org.junit.Test;
import junit.framework.*;
import res.WPTopic;

public class WPTopicTest extends TestCase
{
    private WPTopic topic;

    @Test
    public void testTopicCreated()
    {
        topic = new WPTopic();
        assertNotNull(topic);
    }

    @Test
    public void testTopicCreatedWithParams()
    {
        topic = new WPTopic("title", "topicOwner");

        assertNotNull(topic);
        assertEquals("title", topic.title);
        assertEquals("topicOwner", topic.topicOwner);
    }
}
