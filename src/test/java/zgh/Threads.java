package zgh;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Threads {
    
    private static ExecutorService es = Executors.newCachedThreadPool();
    
    public static void main(String[] args) throws Exception {
        
        
        es.execute(new Runnable() {
            @Override
            public void run() {
                for (int i=0; i<10; i++) {
                    System.out.println(1);
                }
            }
        });
        
    }
    
}

