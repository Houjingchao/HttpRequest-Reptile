package service;

import crawler.HttpRequest;
import Exception.HttpException;
import crawler.HttpRequestBody;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicNameValuePair;
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

    private HttpRequestBase realRequest;

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
     * third formparams
     * fourth header
     * fifth buildconfig
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

    protected HttpRequestBase builRequesForm(String url) throws HttpException {
        HttpRequestBody httpRequestBody = new HttpRequestBody(request.getMethod(), url);
        byte[] bytes = request.getBody();
        if (bytes != null) {
            httpRequestBody.setEntity(new ByteArrayEntity(bytes));
            return httpRequestBody;
        }
        List<BasicNameValuePair> formData = request.getFormData();
        if (formData != null) {// build with forms
            buildWithForms(httpRequestBody);
        }
        return httpRequestBody;
    }

    private void initRealRequest() throws HttpException {
        String url = buildUrlWithParams();
        switch (request.getMethod()) {
            case "POST":
                realRequest = builRequesForm(url);
                break;
            case "GET":
                break;
            default:
                System.out.println("do something");

        }
        buildHeader();

    }

    private void initClient() {

    }

    private void buildWithForms(HttpRequestBody requestBody) {
        HttpEntityEnclosingRequestBase req = (HttpEntityEnclosingRequestBase) requestBody;
        try {
            UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(request.getFormData(), request.getCharset());
        } catch (UnsupportedEncodingException e) {
            throw new HttpException(e);
        }
    }

    private void buildHeader() {
        if (request.getHeader() == null) {
            return;
        }

        for (Map.Entry<String, String> entry : request.getHeader().entrySet()) {
            realRequest.setHeader(entry.getKey(), entry.getValue());
        }

    }
}
