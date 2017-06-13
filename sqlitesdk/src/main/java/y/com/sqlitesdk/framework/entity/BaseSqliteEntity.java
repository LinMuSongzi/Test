package y.com.sqlitesdk.framework.entity;


/**
 * Created by lpds on 2017/4/15.
 */
public abstract class BaseSqliteEntity<T> {

    private int postCode;

    public int getPostCode() {
        return postCode;
    }

    public T setPostCode(int postCode) {
        this.postCode = postCode;
        return (T) this;
    }
}
