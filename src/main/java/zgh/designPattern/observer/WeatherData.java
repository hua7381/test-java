package zgh.designPattern.observer;

import java.util.ArrayList;
import java.util.List;

public class WeatherData implements Subject {
    private List<Observer> observers;
    private Data data;
    
    public WeatherData() {
        this.observers = new ArrayList<Observer>();
    }

    @Override
    public void registerObserver(Observer obs) {
        observers.add(obs);
    }

    @Override
    public void removeObserver(Observer obs) {
        observers.remove(obs);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(data);
        }
    }
    
    public void setMeasurements(Data data) {
        this.data = data;
        this.measurementsChanged();
    }

    public void measurementsChanged() {
        this.notifyObservers();
    }

}
