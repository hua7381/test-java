package zgh.designPattern.strategy;

public class ModelDuck extends Duck {

    public ModelDuck() {
        super.quackBehavior = new Quack();
        super.flyBehavior = new FlyNoWay();
    }
    
    @Override
    public void display() {
        System.out.println("\nI'm a model duck");
    }

}
