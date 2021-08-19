package zgh.designPattern.observer;

public class ForcastDisplay implements Observer, Displayer {
    private Data data;
    
    @Override
    public void update(Data data) {
        this.data = data;
        this.display();
    }

    @Override
    public void display() {
        System.out.println(String.format("<ForcastDisplay>: %s, %s, %s", data.getTemperature(), data.getHumidity(), data.getPressure()));
    }

}
