package zgh.designPattern.command;

public class NoCommand implements Command {

    @Override
    public void excute() {
        System.out.println("do nothing");
    }

}
