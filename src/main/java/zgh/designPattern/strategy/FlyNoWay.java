package zgh.designPattern.strategy;

public class FlyNoWay implements FlyBehavior {

    @Override
    public void fly() {
        System.out.println("i can't fly");
    }

}
