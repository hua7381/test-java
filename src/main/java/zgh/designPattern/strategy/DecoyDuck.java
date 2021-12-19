package zgh.designPattern.strategy;

public class DecoyDuck extends Duck {
    
    public DecoyDuck() {
        super.quackBehavior = new MuteQuack();
        super.flyBehavior = new FlyNoWay();
    }

    @Override
    public void display() {
        System.out.println("\nI'm a Decoy Duck.");
    }

}
