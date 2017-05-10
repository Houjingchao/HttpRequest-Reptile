package HttpDriver;

import Exception.HttpException;
import crawler.HttpRequest;
import crawler.Response;

/**
 * Created by Hjc on 2017/5/11.
 */
public interface IHashHttpDriver {
    Response execute(String sessionId, HttpRequest request) throws HttpException;
}
