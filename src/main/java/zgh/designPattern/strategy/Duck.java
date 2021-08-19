package zgh.designPattern.strategy;

public abstract class Duck {
    protected FlyBehavior flyBehavior;
    protected QuackBehavior quackBehavior;

    //需由子类实现的
    abstract public void display();
    
    //共用的
    public void swim() {
        System.out.println("swim");
    }
    
    //经常发生变化的,有多重不同实现，但又具有复用性的
    public void performFly() {
        flyBehavior.fly();
    }
    public void performQuack() {
        quackBehavior.quack();
    }
    
    //允许动态改变行为
    public void setFlyBehavior(FlyBehavior flyBehavior) {
        this.flyBehavior = flyBehavior;
    }
    
}
