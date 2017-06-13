package y.com.sqlitesdk.framework.entity.respone;

import y.com.sqlitesdk.framework.entity.BaseSqliteEntity;

/**
 * Created by lpds on 2017/4/20.
 */
public class TableMaxCountRespone extends BaseSqliteEntity {

    private long maxCount;

    private String where;


    public TableMaxCountRespone(long maxCount, String where) {
        this.maxCount = maxCount;
        this.where = where;
    }

    public long getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }
}
