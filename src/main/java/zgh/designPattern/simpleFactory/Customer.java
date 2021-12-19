package zgh.designPattern.simpleFactory;

public class Customer {
    
    public static void main(String[] args) {
        Factory factory = new Factory();
        factory.createCar(1);
        factory.createCar(2);
        factory.createCar(1);
    }
    
}
