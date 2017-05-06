package HttpDriver;

import crawler.HttpRequest;
import crawler.Response;
import org.apache.http.HttpException;


/**
 * Created by Hjc on 2017/5/6.
 */
public interface IHttpDriver {
    Response execute(HttpRequest request) throws HttpException;
}
