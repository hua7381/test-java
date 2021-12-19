package zgh.designPattern.strategy;

public class RedheadDuck extends Duck {
    
    public RedheadDuck() {
        super.quackBehavior = new Quack();
        super.flyBehavior = new FlyWithWings();
    }
    
    @Override
    public void display() {
        System.out.println("\nI'm a Redhead Duck");
    }

}
