package service;

import crawler.HttpRequest;
import Exception.HttpException;
import org.apache.http.client.CookieStore;
import org.apache.http.protocol.BasicHttpContext;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Created by Hjc on 2017/5/8.
 */

public class HttpGo {
    private HttpRequest request;
    private BasicHttpContext context;
    private CookieStore cookieStore;

    public HttpGo(HttpRequest request, CookieStore cookieStore) {
        this.request = request;
        this.cookieStore = cookieStore;
        init();
    }

    private void init() {
        context = new BasicHttpContext();
        initRealRequest();
        initClient();
    }

    /**
     *
     * real HttpRequest build
     *
     * first params
     * second body
     * third header
     * fourth formparams
     */

    /**
     * analysis url params
     *
     * @return
     */
    protected String buildUrlWithParams() {
        Map<String, List<String>> paras = request.getQueryParameter();
        if (paras == null) {
            return request.getUrl();
        }
        StringBuilder builder = new StringBuilder();
        builder.append("?");
        for (String key : paras.keySet()) {
            for (String val : paras.get(key)) {
                try {
                    builder.append(URLEncoder.encode(key, request.getCharset()))
                            .append("=")
                            .append(URLEncoder.encode(val, request.getCharset()))
                            .append("&");
                } catch (UnsupportedEncodingException e) {
                    throw new HttpException(e);
                }
            }
        }
        return builder.toString();
    }

    private void initRealRequest() throws HttpException {
        String url = buildUrlWithParams();
        switch (request.getMethod()) {
            case "POST":
                break;
            case "GET":
                break;
            default:
                System.out.println("do something");
        }

    }

    private void initClient() {

    }
}
