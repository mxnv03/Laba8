import java.net.*;

public class URLDepthPair {
    private URL url;
    private int depth;

    public URLDepthPair(String url, int depth) throws MalformedURLException {
        this.url = new URL(url);
        this.depth = depth;
    }

    public URL getUrl()
    {
        return url;
    }

    public int getDepth()
    {
        return depth;
    }

    public String toString()
    {
        return url.toString() + " " + depth;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof URLDepthPair p){
            return url.equals(p.url);
        }
        return false;
    }
    @Override
    public int hashCode()
    {
        return url.hashCode();
    }
}
