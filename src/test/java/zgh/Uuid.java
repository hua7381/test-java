package zgh;

import java.util.UUID;

import org.junit.Test;

public class Uuid {
    
    @Test
    public void fn1() {
        for (int i=0; i<10; i++) {
            System.out.println(UUID.randomUUID().toString().replace("-", "").toUpperCase());
        }
    }
}
