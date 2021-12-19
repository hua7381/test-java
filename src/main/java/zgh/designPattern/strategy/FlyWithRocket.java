package zgh.designPattern.strategy;

public class FlyWithRocket implements FlyBehavior {

    @Override
    public void fly() {
        System.out.println("Fly with rocket!");
    }

}
