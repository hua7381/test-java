package zgh;

import java.util.Base64;

import org.junit.Test;

public class Base64AndString {
    
    @Test
    public void xxx() {
        // String str = "{\"token\":null,\"id\":\"D42E5064E5F743258FB81EE2634EA415\",\"no\":\"chenhao\",\"name\":\"陈浩\",\"deptNo\":\"440606500000\",\"deptName\":\"顺德区局大良派出所\",\"password\":null,\"deptLevel\":3,\"ip\":\"127.0.0.1\",\"roles\":[{\"no\":\"ORDINARY\",\"code\":\"ORDINARY\",\"name\":\"普通用户\"},{\"no\":\"INTELLIGENCE\",\"code\":\"INTELLIGENCE\",\"name\":\"情报员\"},{\"no\":\"INSTRUCTION_LEADER\",\"code\":\"INSTRUCTION_LEADER\",\"name\":\"批示领导\"}]}";
        String str = "{'token':'fd6775f4906d4b17a28de12ba0aaafd0','id':'FCC614F12B6D4FFE801E68F475C7D14B','no':'zgh','name':'张桂华2','deptNo':'440606010000','deptName':'顺德区局指挥中心','ip':'127.0.0.1','deptLevel':3,'roles':[{'code':'ADMIN','name':'管理员'},{'code':'SECRET_VIEWER','name':'保密内容查看者'}]}".replace("'", "\"");
        String base64Str = new String(Base64.getEncoder().encode(str.getBytes()));
        String str2 = new String(Base64.getDecoder().decode(base64Str));
        System.out.println(str);
        System.out.println(base64Str.length());
        System.out.println(base64Str);
        System.out.println(str2);
    }

}
