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

    
    @Test
    public void t2() {
        String str = "eyJkYXRhIjoie1wic3RhdHVzXCI6XCIxXCIsXCJjb3VudFwiOlwiMVwiLFwiaW5mb1wiOlwiT0tcIixcImluZm9jb2RlXCI6XCIxMDAwMFwiLFwiZm9yZWNhc3RzXCI6W3tcImNpdHlcIjpcIumVv+aymeW4glwiLFwiYWRjb2RlXCI6XCI0MzAxMDBcIixcInByb3ZpbmNlXCI6XCLmuZbljZdcIixcInJlcG9ydHRpbWVcIjpcIjIwMjQtMDEtMDUgMTQ6MDQ6NDJcIixcImNhc3RzXCI6W3tcImRhdGVcIjpcIjIwMjQtMDEtMDVcIixcIndlZWtcIjpcIjVcIixcImRheXdlYXRoZXJcIjpcIuWwj+mbqFwiLFwibmlnaHR3ZWF0aGVyXCI6XCLpmLRcIixcImRheXRlbXBcIjpcIjE0XCIsXCJuaWdodHRlbXBcIjpcIjhcIixcImRheXdpbmRcIjpcIuS4nFwiLFwibmlnaHR3aW5kXCI6XCLkuJxcIixcImRheXBvd2VyXCI6XCIxLTNcIixcIm5pZ2h0cG93ZXJcIjpcIjEtM1wiLFwiZGF5dGVtcF9mbG9hdFwiOlwiMTQuMFwiLFwibmlnaHR0ZW1wX2Zsb2F0XCI6XCI4LjBcIn0se1wiZGF0ZVwiOlwiMjAyNC0wMS0wNlwiLFwid2Vla1wiOlwiNlwiLFwiZGF5d2VhdGhlclwiOlwi5aSa5LqRXCIsXCJuaWdodHdlYXRoZXJcIjpcIuWkmuS6kVwiLFwiZGF5dGVtcFwiOlwiMTZcIixcIm5pZ2h0dGVtcFwiOlwiNlwiLFwiZGF5d2luZFwiOlwi5LicXCIsXCJuaWdodHdpbmRcIjpcIuS4nFwiLFwiZGF5cG93ZXJcIjpcIjEtM1wiLFwibmlnaHRwb3dlclwiOlwiMS0zXCIsXCJkYXl0ZW1wX2Zsb2F0XCI6XCIxNi4wXCIsXCJuaWdodHRlbXBfZmxvYXRcIjpcIjYuMFwifSx7XCJkYXRlXCI6XCIyMDI0LTAxLTA3XCIsXCJ3ZWVrXCI6XCI3XCIsXCJkYXl3ZWF0aGVyXCI6XCLlsI/pm6hcIixcIm5pZ2h0d2VhdGhlclwiOlwi5bCP6ZuoXCIsXCJkYXl0ZW1wXCI6XCIxM1wiLFwibmlnaHR0ZW1wXCI6XCI4XCIsXCJkYXl3aW5kXCI6XCLkuJxcIixcIm5pZ2h0d2luZFwiOlwi5LicXCIsXCJkYXlwb3dlclwiOlwiMS0zXCIsXCJuaWdodHBvd2VyXCI6XCIxLTNcIixcImRheXRlbXBfZmxvYXRcIjpcIjEzLjBcIixcIm5pZ2h0dGVtcF9mbG9hdFwiOlwiOC4wXCJ9LHtcImRhdGVcIjpcIjIwMjQtMDEtMDhcIixcIndlZWtcIjpcIjFcIixcImRheXdlYXRoZXJcIjpcIuWwj+mbqFwiLFwibmlnaHR3ZWF0aGVyXCI6XCLlsI/pm6hcIixcImRheXRlbXBcIjpcIjE1XCIsXCJuaWdodHRlbXBcIjpcIjdcIixcImRheXdpbmRcIjpcIuS4nFwiLFwibmlnaHR3aW5kXCI6XCLkuJxcIixcImRheXBvd2VyXCI6XCIxLTNcIixcIm5pZ2h0cG93ZXJcIjpcIjEtM1wiLFwiZGF5dGVtcF9mbG9hdFwiOlwiMTUuMFwiLFwibmlnaHR0ZW1wX2Zsb2F0XCI6XCI3LjBcIn1dfV19Iiwic3RhdHVzIjoiMjAwIn0=";
        String str2 = new String(Base64.getDecoder().decode(str));
        System.out.println(str);
        System.out.println(str2);
    }

}
