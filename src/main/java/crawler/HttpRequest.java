package crawler;

import HttpDriver.IHttpDriver;
import org.apache.http.HttpException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.cookie.Cookie;
import org.apache.http.message.BasicNameValuePair;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Hjc on 2017/5/6.
 */
public class HttpRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private int timeout = 30 * 1000;
    private String cookieType = CookieSpecs.NETSCAPE;
    private String session;
    private String url;
    private String method;
    private Map<String, List<String>> queryParameter;
    private List<BasicNameValuePair> formData;
    private Map<String, String> header;
    private List<Cookie> cookie;
    private List<Cookie> deleteCookies;
    private Boolean isClear = false;
    private int PDU = -1;
    private String charset = "utf-8";
    private String responseCharset;

    public String getCookieType() {
        return cookieType;
    }

    public HttpRequest setCookieType(String cookieType) {
        this.cookieType = cookieType;
        return this;
    }

    private Map<String, Object> extra;

    /* 是否自动跳转 */
    private Boolean isRedirectsEnabled = true;

    private byte[] body;

    private transient IHttpDriver driver;


    public HttpRequest POST() {
        this.setMethod("POST");
        return this;
    }

    public HttpRequest GET() {
        this.setMethod("GET");
        return this;
    }

    public HttpRequest DELETE() {
        this.setMethod("DELETE");
        return this;
    }

    public HttpRequest PUT() {
        this.setMethod("GET");
        return this;
    }

    public HttpRequest PATCH() {
        this.setMethod("PATCH");
        return this;
    }

    public HttpRequest OPTIONS() {
        this.setMethod("OPTIONS");
        return this;
    }

    public HttpRequest HEAD() {
        this.setMethod("HEAD");
        return this;
    }


    public List<Cookie> getDeleteCookies() {
        return deleteCookies;
    }

    public void setDeleteCookies(List<Cookie> deleteCookies) {
        this.deleteCookies = deleteCookies;
    }

    public List<Cookie> getCookie() {
        return cookie;
    }

    public void setCookie(List<Cookie> cookie) {
        this.cookie = cookie;
    }

    public Map<String, List<String>> getQueryParameter() {
        return queryParameter;
    }

    public void setQueryParameter(Map<String, List<String>> queryParameter) {
        this.queryParameter = queryParameter;
    }

    public List<BasicNameValuePair> getFormData() {
        return formData;
    }

    public void setFormData(List<BasicNameValuePair> formData) {
        this.formData = formData;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public Boolean getIsClear() {
        return isClear;
    }

    public void setIsClear(Boolean isClear) {
        this.isClear = isClear;
    }

    public Boolean getRedirectsEnabled() {
        return isRedirectsEnabled;
    }

    public HttpRequest setRedirectsEnabled(Boolean isRedirectsEnabled) {
        this.isRedirectsEnabled = isRedirectsEnabled;
        return this;
    }

    public String getSession() {
        return session;
    }

    public HttpRequest setSession(String session) {
        this.session = session;
        return this;
    }

    public int getPDU() {
        return PDU;
    }

    public HttpRequest setPDU(int pdu) {
        this.PDU = pdu;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public HttpRequest setUrl(String url) {
        this.url = url;
        return this;
    }

    public HttpRequest url(String url) {
        this.url = url;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public HttpRequest setMethod(String method) {
        this.method = method;
        return this;
    }

    public byte[] getBody() {
        return body;
    }

    public HttpRequest setBody(byte[] body) {
        this.body = body;
        return this;
    }

    public String getCharset() {
        return charset;
    }

    public HttpRequest setCharset(String charset) {
        this.charset = charset;
        if (responseCharset == null) {
            responseCharset = charset;
        }
        return this;
    }

    public String getResponseCharset() {
        return responseCharset;
    }

    public HttpRequest setResponseCharset(String responseCharset) {
        this.responseCharset = responseCharset;
        return this;
    }

    public HttpRequest queryPara(String name, String value) {
        if (this.queryParameter == null) {
            this.queryParameter = new HashMap<String, List<String>>();
        }
        if (this.queryParameter.get(name) == null) {
            this.queryParameter.put(name, new ArrayList<String>());
        }

        this.queryParameter.get(name).add(value);
        return this;
    }

    public HttpRequest form(String name, String value) {
        if (this.formData == null) {
            this.formData = new ArrayList<BasicNameValuePair>();
        }
        if (name == null) {
            return this;
        }
        this.formData.add(new BasicNameValuePair(name, value));
        return this;
    }

    public HttpRequest singleForm(String name, String value) {
        if (this.formData == null) {
            this.formData = new ArrayList<BasicNameValuePair>();
        }
        for (int i = 0; i < this.formData.size(); i++) {
            BasicNameValuePair nv = this.formData.get(i);
            if (nv.getName().equals(name)) {
                this.formData.remove(nv);
                i--;
            }
        }
        this.formData.add(new BasicNameValuePair(name, value));
        return this;
    }

    public HttpRequest head(String name, String value) {
        if (this.header == null) {
            this.header = new HashMap<String, String>();
        }
        this.header.put(name, value);
        return this;
    }


    public HttpRequest clearCookie() {
        this.isClear = true;
        return this;
    }

    public HttpRequest deleteCookie(Cookie cookie) {
        if (this.deleteCookies == null) {
            this.deleteCookies = new ArrayList<Cookie>();
        }
        this.deleteCookies.add(cookie);
        return this;

    }

    public HttpRequest addCookie(Cookie cookie) {
        if (this.cookie == null) {
            this.cookie = new ArrayList<Cookie>();
        }
        this.cookie.add(cookie);
        return this;
    }

    public Response execute() throws HttpException {
        if (this.getResponseCharset() == null) {
            this.setResponseCharset(this.getCharset());
        }
        Long time = System.currentTimeMillis();
        Response response = null;
        try {
            response = this.driver.execute(this);
        } catch (HttpException e) {
            throw e;
        }
        return response;
    }

    public HttpRequest driver(IHttpDriver driver) {
        this.driver = driver;
        return this;
    }

    public HttpRequest setCustomTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, Object> extra) {
        this.extra = extra;
    }
}
