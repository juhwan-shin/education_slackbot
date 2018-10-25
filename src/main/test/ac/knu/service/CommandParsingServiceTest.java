package ac.knu.service;


import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandParsingServiceTest {

    private CommandParsingService commandParsingService = new CommandParsingService();

    @Test
    public void 봇_호출시_설명을_반환해야한다() {
        String command = commandParsingService.parseCommand("@group5bot");
        assertEquals(command, "-------------------------------------\n" +
                "1.add      (ex.add 홍길동 20 m)\n" +
                "2.remove   (ex.remove 홍길동)\n" +
                "3.list     (ex.list)\n" +
                "4.find     (ex.find 홍길동)\t (ex. find 김* : 김씨 성을 가진 사람 찾기)" +
                "-------------------------------------");
    }

    @Test
    public void 봇은_리스트커맨드를_받앗을때_현재친구리스트를_반환해야한다() {
        commandParsingService.parseCommand("@group5bot add 아이유 24 F");
        String result = commandParsingService.parseCommand("@group5bot list");
        assertEquals(result, "아이유 24 F\n");
    }

    @Test
    public void 정상적인_add_명령어의_사용_시_추가했다는_알림을_반환해야한다() {
        String result = commandParsingService.parseCommand("@group5bot add 아이유 24 F");
        assertEquals(result, "아이유님을 추가했습니다.");
    }

    @Test
    public void 나이에_숫자형식이_입력되지_않는다면_에러메세지를_반환해야한다() {
        String result = commandParsingService.parseCommand("@group5bot add 아이구 ab M");
        assertEquals(result,"나이를 숫자형식으로 입력해주세요.");
    }

    @Test
    public void 잘못된_명령어_형식에_대해_에러메세지를_반환해야한다() {
        String result = commandParsingService.parseCommand("@group5bot add 아이미");
        assertEquals(result,"명령어 형식에 어긋납니다.\nadd 이름 나이 성별(M or F)의 형식으로 명령문을 작성해주세요.");
    }

    @Test
    public void 리스트가_비어있을때_친구삭제를_하면_아무것도_없다는_반환을_해야한다() {
        String result = commandParsingService.parseCommand("@group5bot remove 친구이름");
        assertEquals(result,"친구 리스트에 아무것도 존재하지 않습니다.");
    }

    @Test
    public void 친구리스트에_해당친구가_없을때_친구삭제를_하면_없다는_반환을_해야한다() {
        commandParsingService.parseCommand("@group5bot add 신주환 24 M");
        String result = commandParsingService.parseCommand("@group5bot remove 홍길동");
        assertEquals(result,"홍길동님이 친구 리스트에 존재하지 않습니다.");
    }

    @Test
    public void 이미_리스트에_존재하는_이름을_추가할_때_에러메세지를_반환해야한다(){
        commandParsingService.parseCommand("@group5bot add 아이유 24 F");
        String result = commandParsingService.parseCommand("@group5bot add 아이유 24 F");
        assertEquals(result,"이미 아이유님이 존재합니다. remove 명령어로 제거 후 새로 추가해 주시기 바랍니다.");
    }

    @Test
    public void 리스트_크기가_10이상일_때_에러메세지를_반환해야한다(){
        commandParsingService.parseCommand("@group5bot add 아이일 24 F");
        commandParsingService.parseCommand("@group5bot add 아이이 24 F");
        commandParsingService.parseCommand("@group5bot add 아이삼 24 F");
        commandParsingService.parseCommand("@group5bot add 아이사 24 F");
        commandParsingService.parseCommand("@group5bot add 아이오 24 F");
        commandParsingService.parseCommand("@group5bot add 아이육 24 F");
        commandParsingService.parseCommand("@group5bot add 아이칠 24 F");
        commandParsingService.parseCommand("@group5bot add 아이팔 24 F");
        commandParsingService.parseCommand("@group5bot add 아이구 24 F");
        commandParsingService.parseCommand("@group5bot add 아이십 24 F");
        String result = commandParsingService.parseCommand("@group5bot add 아이유 24 F");

        assertEquals(result,"리스트의 size가 10을 넘었습니다. 새로운 멤버를 추가하시려면 기존 멤버를 제거해주세요.");
    }

    @Test
    public void M_F_이외의_성별을_입력했을_때_에러메세지를_반환해야한다(){
        String result = commandParsingService.parseCommand("@group5bot add 아이유 24 D");
        assertEquals(result,"정확한 성별 정보를 입력해주세요.");
    }

    @Test
    public void 리스트에_있는_친구를_찾을_때_정상_출력해야한다() {
        commandParsingService.parseCommand("@group5bot add 아이유 24 F");
        String result = commandParsingService.parseCommand("@group5bot find 아이유");
        assertEquals(result,"아이유 24 F");
    }

    @Test
    public void 리스트에_없는_친구를_찾을_때_적절한_오류메시지를_반환해야한다() {
        commandParsingService.parseCommand("@group5bot add 김홍진 22 M");
        String result = commandParsingService.parseCommand("@group5bot find 아이유");
        assertEquals(result, "해당하는 이름을 가진 사람이 없습니다.");
    }

    @Test
    public void 리스트에서_특정_성씨를_포함한_사람들을_반환해야_한다() {
        commandParsingService.parseCommand("@group5bot add 김홍진 22 M");
        commandParsingService.parseCommand("@group5bot add 김하이 22 M");
        String result = commandParsingService.parseCommand("@group5bot find 김*");
        assertEquals(result, "해당 성씨를 가진 친구목록\n[김하이, 김홍진]");

    }
}
