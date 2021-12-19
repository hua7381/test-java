package zgh.designPattern.command;

public class RemoteControl {
    private Command[] commands = null;
    public RemoteControl() {
        commands = new Command[6];
        for (int i=0; i<commands.length; i++) {
            commands[i] = new NoCommand();
        }
    }
    public Command[] getCommands() {
        return commands;
    }
    public void setCommands(Command[] commands) {
        this.commands = commands;
    }
}
