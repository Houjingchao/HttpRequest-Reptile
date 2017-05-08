package Exception;

/**
 * Created by Hjc on 2017/5/8.
 */
public class HttpException extends RuntimeException {
    static final long serialVersionUID = 4L;
    public HttpException(String message){
        super(message);
    }
    public HttpException(String message , Throwable cause){
        super(message,cause);
    }
    public HttpException(Throwable cause){
        super(cause);
    }
}