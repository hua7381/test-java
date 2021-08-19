package zgh.designPattern.observer;

public class WeatherStation {

    public static void main(String[] args) {
        WeatherData w = new WeatherData();

        Observer obs1 = new CurrentConditionsDisplay();
        Observer obs2 = new ForcastDisplay();

        w.registerObserver(obs1);
        w.setMeasurements(new Data(11.0f, 22.0f, 33.0f));
        
        w.registerObserver(obs2);
        w.setMeasurements(new Data(21.0f, 22.0f, 23.0f));
        
        w.removeObserver(obs1);
        w.setMeasurements(new Data(31.0f, 32.0f, 33.0f));
        
        w.registerObserver(obs1);
        w.setMeasurements(new Data(41.0f, 42.0f, 43.0f));
    }

}
