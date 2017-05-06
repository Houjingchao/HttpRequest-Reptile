package crawler;

import org.apache.http.HttpException;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Hjc on 2017/5/6.
 */
public interface Response extends Serializable {
    int code();

    String lastUrl();

    Map<String, String> header() throws HttpException;

    String string() throws HttpException;


    byte[] bin() throws HttpException;
}
