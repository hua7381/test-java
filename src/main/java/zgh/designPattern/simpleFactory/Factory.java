package zgh.designPattern.simpleFactory;

public class Factory {

    public Car createCar(int type) {
        switch(type) {
        case 1: return new CarA();
        case 2: return new CarB();
        }
        return null;
    }

}
