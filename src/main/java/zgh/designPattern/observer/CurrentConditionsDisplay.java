package zgh.designPattern.observer;

public class CurrentConditionsDisplay implements Observer, Displayer {
    private Data data;

    @Override
    public void update(Data data) {
        this.data = data;
        this.display();
    }

    @Override
    public void display() {
        System.out.println(String.format("<CurrentConditionsDisplay>: %s, %s, %s", data.getTemperature(), data.getHumidity(), data.getPressure()));
    }

}
