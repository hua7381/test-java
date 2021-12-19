package zgh.designPattern.command;

public class Client {
    public static void main(String[] args) {
        RemoteControl control = new RemoteControl();

        Light light1 = new Light("kitchenLight");
        Light light2 = new Light("roomLight");

        control.getCommands()[0] = new LightOnCommand(light1);
        control.getCommands()[1] = new LightOffCommand(light1);
        control.getCommands()[2] = new LightOnCommand(light2);
        control.getCommands()[3] = new LightOffCommand(light2);
        

        control.getCommands()[0].excute();
        control.getCommands()[1].excute();
        control.getCommands()[2].excute();
        control.getCommands()[3].excute();
    }
}