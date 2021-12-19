package zgh.designPattern.command;

public class Light {
    private String name;
    public Light(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void on() {
        System.out.println(name+" is on");
    }
    public void off() {
        System.out.println(name+" is off");
    }
}