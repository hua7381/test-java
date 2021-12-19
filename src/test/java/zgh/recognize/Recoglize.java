package zgh.recognize;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;

public class Recoglize {

    public static void main(String[] args) {

        Recoglize inst = new Recoglize();
        inst.test("");
        inst.test("内容:事主报:11:30分被人以退货理赔方式诈骗，在对方发送的链接中填写了银行卡号与密码，被转走24000元，事主工商银行卡号（621226000065378909，6212263202010338084），通过电话，微信联系对方。");
        inst.test("事主李柏芬（男，54岁，容桂人，联系电话：13902564152）报称其儿子李嘉垣（男，22岁，容桂人，身份证：440681199801295931，电话：17620638378，聋哑人员）于8月31日17时许在阿斯蒂芬横四路1号阿斯蒂芬公司离开后至今未归。特征：身高178CM，中等身材，短发。目前，案件由阿斯蒂芬中队作一步跟进");
        // inst.test("报警人来电称:现在上址接到一个诈骗电话（18675012563），报警人无经济损失，需求助警察跟进");
        // inst.test("市局指挥中心接报在大良五沙东新高速入口路段，发生交通事故。经了解，现场一辆重型栏板货车（粤JM9260）与一辆无牌电动车发生碰撞");
        // inst.test("华东路一巷内发，查获一辆货车（粤E995NF）");
        // inst.test("现时上址，其小车（粤ETX614）撞到路边的两台小车（粤X58M78，粤XTX713）相碰，人无事，需交警处理。内容:更正:事主报:现时上址，其小车（粤ETX614）撞到路边的两台小车（粤X58M78，粤XTX713），对方车主不在场，人无事，求助交警处理。");

    }

    private void test(String content) {
        System.out.println("\n" + content);
        System.out.println("bankCard: " + JSON.toJSON(this.recBankCard(content)));
        System.out.println("idcard: " + JSON.toJSON(this.recIdCard(content)));
        System.out.println("mobile: " + JSON.toJSON(this.recMobile(content)));
        System.out.println("plate: " + JSON.toJSON(this.recPlate(content)));
    }

    private Object recMobile(String content) {
        String reg = "1[3|4|5|7|8][0-9]{9}";
        return recoglizeIdCarad(content, reg);
    }

    private List<String> recIdCard(String content) {
        String reg = 
            "[1-9]\\d{5}" + 
            "(19|20)\\d{2}" + // 年
            "((0[1-9])|(10|11|12))" + // 月
            "(([0-2][1-9])|10|20|30|31)" + // 日
            "\\d{3}[0-9Xx]";
        return recoglizeIdCarad(content, reg);
    }

    private List<String> recBankCard(String content) {
        String reg = 
        "(62\\d{17})|(62\\d{14})";
        return recoglizeIdCarad(content, reg);
    }

    private List<String> recPlate(String content) {
        String reg = "([京津晋冀蒙辽吉黑沪苏浙皖闽赣鲁豫鄂湘粤桂琼渝川贵云藏陕甘青宁新][ABCDEFGHJKLMNPQRSTUVWXY][1-9DF][1-9ABCDEFGHJKLMNPQRSTUVWXYZ]\\d{3}[1-9DF]|[京津晋冀蒙辽吉黑沪苏浙皖闽赣鲁豫鄂湘粤桂琼渝川贵云藏陕甘青宁新][ABCDEFGHJKLMNPQRSTUVWXY][\\dABCDEFGHJKLNMxPQRSTUVWXYZ]{5})";
        return recoglizeIdCarad(content, reg);
    }

    private List<String> recoglizeIdCarad(String content, String reg) {
        Matcher mc = Pattern.compile(reg).matcher(content);
        
        List<String> result = new ArrayList<String>();
        while(mc.find()) {
            result.add(mc.group());
        }
        return result;
    }

}