package zgh.designPattern.strategy;

public class MallardDuck extends Duck {
    
    public MallardDuck() {
        super.quackBehavior = new Quack();
        super.flyBehavior = new FlyWithWings();
    }

    @Override
    public void display() {
        System.out.println("\nI'm a mallard duck");
    }

}
