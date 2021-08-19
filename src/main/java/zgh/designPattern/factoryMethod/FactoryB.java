package zgh.designPattern.factoryMethod;

public class FactoryB extends Factory {

    @Override
    public Car createCar() {
        return new CarB();
    }

}
