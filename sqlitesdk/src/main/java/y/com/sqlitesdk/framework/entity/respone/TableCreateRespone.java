package y.com.sqlitesdk.framework.entity.respone;

import y.com.sqlitesdk.framework.entity.BaseSqliteEntity;

/**
 * Created by lpds on 2017/4/15.
 */
public class TableCreateRespone extends BaseSqliteEntity {

    private String tableName;
    private boolean isOk;

    public TableCreateRespone(boolean isOk, String tableName) {
        this.isOk = isOk;
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }
}
