package zgh.designPattern.strategy;

public class RubberDuck extends Duck {
    
    public RubberDuck() {
        super.quackBehavior = new Squeak();
        super.flyBehavior = new FlyNoWay();
    }

    @Override
    public void display() {
        System.out.println("\nI'm a rubber duck");
    }
    
}
