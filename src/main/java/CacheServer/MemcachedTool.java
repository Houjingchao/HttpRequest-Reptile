package CacheServer;

/**
 * Created by Hjc on 2017/5/11.
 */
public class MemcachedTool {
    private static MemcachedTool memcachedTool = new MemcachedTool();

    private static MemcachedTool newInstace(String name) {
        return memcachedTool;
    }
}
