package crawler;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import java.net.URI;

/**
 * Created by Hjc on 2017/5/9.
 */
public class HttpRequestBody extends HttpEntityEnclosingRequestBase {
    private String method;

    public HttpRequestBody(String method, final String url) {
        super();
        this.method = method;
        setURI(URI.create(url));
    }

    @Override
    public String getMethod() {
        return method;
    }
}
