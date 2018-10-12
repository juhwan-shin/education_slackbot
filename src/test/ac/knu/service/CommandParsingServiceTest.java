package ac.knu.service;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class CommandParsingServiceTest {

    private CommandParsingService commandParsingService;

    @Before
    public void setUp(){
        commandParsingService = new CommandParsingService();
    }

    @Test
    public void bot_should_understand_list_command() {

        String command = commandParsingService.parseCommand("list");
        Assert.assertTrue(command.equalsIgnoreCase("time,add"));
    }

    @Test
    public void 봇은_타임커맨드를_받앗을때_현재시간을_반환해야한다(){

        String result = commandParsingService.parseCommand("time");

        Assert.assertTrue(result.contains("Current Time is :"));
    }

}
