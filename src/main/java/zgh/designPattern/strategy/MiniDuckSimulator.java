package zgh.designPattern.strategy;

public class MiniDuckSimulator {

    public static void main(String[] args) {
        Duck d = new MallardDuck();
        d.display();
        d.performQuack();
        d.performFly();
        
        d = new DecoyDuck();
        d.display();
        d.performQuack();
        d.performFly();
        
        d = new RedheadDuck();
        d.display();
        d.performQuack();
        d.performFly();
        
        d = new RubberDuck();
        d.display();
        d.performQuack();
        d.performFly();
        
        d = new ModelDuck();
        d.display();
        d.performQuack();
        d.performFly();
        
        d.setFlyBehavior(new FlyWithRocket());//动态改变行为
        d.performFly();
    }

}
