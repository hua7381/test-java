package zgh.designPattern.observer;

public class Data {
    private Float temperature;
    private Float humidity;
    private Float pressure;
    
    public Data() {
    }
    
    public Data(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
    }
    
    public Float getTemperature() {
        return temperature;
    }
    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }
    public Float getHumidity() {
        return humidity;
    }
    public void setHumidity(Float humidity) {
        this.humidity = humidity;
    }
    public Float getPressure() {
        return pressure;
    }
    public void setPressure(Float pressure) {
        this.pressure = pressure;
    }
}
