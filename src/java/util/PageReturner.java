package util;

import java.util.ResourceBundle;

/**
 *
 * @author Melnikov
 */
public class PageReturner {
    private final static ResourceBundle pageName = ResourceBundle.getBundle("properties.pageAlias");
    public static String getPage(String key){
        return pageName.getString(key);
    }
}
