import java.util.LinkedList;
public class URLPool {
    private final LinkedList<URLDepthPair> openedLinks = new LinkedList<>();
    private final LinkedList<URLDepthPair> closedLinks = new LinkedList<>();

    private int numWaiters = 0;

    public synchronized URLDepthPair getPair() {
        while (openedLinks.size() == 0){
            numWaiters++;
            try{
                wait(); // заставляют поток ждать
            } catch (InterruptedException e) {
            }
            numWaiters--;
        }
        return openedLinks.removeFirst();
    }

    public synchronized void push (URLDepthPair pair) {
        if (!openedLinks.contains(pair)) openedLinks.add(pair); //проверяем, есть ли такая ссылка, если нет, то добавляем
        notify(); // выводит поток из ожидания
    }

    public void pushClosed(URLDepthPair pair){
        if (!closedLinks.contains(pair)) closedLinks.add(pair);
    }

    public LinkedList<URLDepthPair> getClosedLinks(){
        return closedLinks;
    }

    public int getNumWaiters() {
        return numWaiters;
    }
}
