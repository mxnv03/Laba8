import java.net.MalformedURLException;

public class Crawler {
    public static final String ERROR = "usage: java Crawler <URL><depth><numThreads>";
    public static void main(String[] args){
        if (args.length != 3){
            System.out.println (ERROR);
            return;
        }
        int numThreads;
        int maxDepth;
        try {
            maxDepth = Integer.parseInt(args[1]);
            numThreads = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.out.println(ERROR);
            return;
        }
        URLDepthPair firstPair;
        try {
            firstPair = new URLDepthPair(args[0], 0);
        } catch (MalformedURLException e) {
            System.out.println(ERROR);
            return;
        }
        URLPool pool = new URLPool();
        pool.push(firstPair);
        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++){
            CrawlerTask task = new CrawlerTask(pool, maxDepth);
            threads[i] = new Thread(task);
            threads[i].start(); //запустили потоки
        }
        while (pool.getNumWaiters() != numThreads) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
        }
        for (int i=0; i < numThreads; i++) {
            threads[i].stop();
        }
        System.out.println("Обработка завершена");
        for (URLDepthPair pair: pool.getClosedLinks()) {
            System.out.println(pair);
        }
    }
}
