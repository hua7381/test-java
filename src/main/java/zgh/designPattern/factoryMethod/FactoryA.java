package zgh.designPattern.factoryMethod;

public class FactoryA extends Factory {

    @Override
    public Car createCar() {
        return new CarA();
    }

}
