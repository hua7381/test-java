package zgh.designPattern.factoryMethod;

public class Customer {
    public static void main(String[] args) {
        Factory factory = new FactoryB();
        factory.createCar();
        
        factory = new FactoryA();
        factory.createCar();
    }
}
