package ac.knu.service;

import ac.knu.service.Human;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommandParsingService {

    List<String> commandList = new ArrayList();
    TreeSet<String> dict = new TreeSet<String>();
    TreeMap<String, Human> lists = new TreeMap<String, Human>();
    public CommandParsingService(){
        commandList.add("time");
        commandList.add("add");
    }

    public String parseCommand(String command) {

        String[] commandSplitList = command.split(" ");
        //아무것도 입력되지않았을때의 예외처리 필요
        if(commandSplitList.length==1) {
            return showCommand();
        }
        else {
            command = commandSplitList[1];
        }
        if(command.equalsIgnoreCase("time")){
            return "Current Time is :" + new Date();
        }
        else if(command.equalsIgnoreCase("add")){
            return addCommand(commandSplitList);
        }
        else if(command.equalsIgnoreCase("remove")) {
            return removeCommand(commandSplitList);
        }
        else if(command.equalsIgnoreCase("list")) {
            return listCommand(commandSplitList);
        }
        else if (command.equalsIgnoreCase("find")) {
            return findCommand(commandSplitList);
        }


        return "사용 가능한 명령어를 입력해주세요.";
    }

    public String showCommand() {
        String commandListShow = "-------------------------------------\n" +
                "1.add      (ex.add 홍길동 20 m)\n" +
                "2.remove   (ex.remove 홍길동)\n" +
                "3.list     (ex.list)\n" +
                "4.find     (ex.find 홍길동)\t (ex. find 김* : 김씨 성을 가진 사람 찾기)" +
                "-------------------------------------";

        return commandListShow;
    }

    public String addCommand(String [] commandSplitList){
        //이미 dict에 있을 경우 예외처리 필요
        String name; int age; char sex;
        if(lists.size()>=10)
            return "리스트의 size가 10을 넘었습니다. 새로운 멤버를 추가하시려면 기존 멤버를 제거해주세요.";
        try {
            name = commandSplitList[2];
            age = Integer.parseInt(commandSplitList[3]);
            sex = commandSplitList[4].toUpperCase().charAt(0);
            if(sex!='M'&&sex!='F')
                return "정확한 성별 정보를 입력해주세요.";
        }
        catch(NumberFormatException e){
            return "나이를 숫자형식으로 입력해주세요.";
        }
        catch(Exception e){
            return "명령어 형식에 어긋납니다.\nadd 이름 나이 성별(M or F)의 형식으로 명령문을 작성해주세요.";
        }
        Human human = new Human(name, age, sex);
        if(!dict.contains(name)) {
            dict.add(name);
            lists.put(name, human);
            return name+"님을 추가했습니다.";
        }
        else{
            return "이미 "+name+"님이 존재합니다. remove 명령어로 제거 후 새로 추가해 주시기 바랍니다.";
        }
    }

    public String removeCommand(String[] commandSplitList) {
        String removeName = commandSplitList[2];
        if(dict.isEmpty()) {
            return "친구 리스트에 아무것도 존재하지 않습니다.";
        }
        if(dict.contains(removeName)) {
            lists.remove(removeName);
            dict.remove(removeName);
            return removeName + "님을 삭제하였습니다.";
        }
        else {
            return removeName + "님이 친구 리스트에 존재하지 않습니다.";
        }
    }

    public String listCommand(String [] commandSplitList) {
        Set<String> set = lists.keySet();
        //예외처리 :아무것도 없을떄 예외처리
        if(set.isEmpty()){
            return "리스트에 목록이 존재하지 않습니다.";
        }
        String result = "";
        StringBuilder sb = new StringBuilder();
        for(String i : set){
            sb.append(lists.get(i).toString());
            sb.append("\n");
        }
        result = sb.toString();
        return result;
    }

    public String findCommand(String[] commandSplitList) {
        String name = commandSplitList[2];
        if (!dict.contains(name)) {
            LinkedList<String> unkName = new LinkedList<String>();
            if(name.contains("*")) {
                Set<String> friendsName = lists.keySet();
                String lastName = name.substring(0, 1);
                for (String friend : friendsName) {
                    if (friend.substring(0,1).equals(lastName)) {
                        unkName.add(friend);
                    }
                }
                return "해당 성씨를 가진 친구목록\n" + unkName.toString();
            }
            return "해당하는 이름을 가진 사람이 없습니다.";
        }
        else {
            Human friend = lists.get(name);
            return friend.toString();
        }
    }

}