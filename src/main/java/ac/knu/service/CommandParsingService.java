package ac.knu.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommandParsingService {

    List<String> commandList = new ArrayList();

    public CommandParsingService(){
        commandList.add("time");
        commandList.add("add");
    }

    public String parseCommand(String command) {

        String[] commandSplitList = command.split(" ");
        command = commandSplitList[1];
        if(command.equalsIgnoreCase("time")){
            return "Current Time is :" + new Date();
        }


        String result ="";



        for(int i = 0; i<commandList.size();i++) {
           result = result + commandList.get(i) +",";

        }

        result = result.substring(0,result.lastIndexOf(","));

        return result;
    }
}
