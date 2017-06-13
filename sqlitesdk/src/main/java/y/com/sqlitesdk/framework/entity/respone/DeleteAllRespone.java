package y.com.sqlitesdk.framework.entity.respone;

import y.com.sqlitesdk.framework.entity.BaseSqliteEntity;

/**
 * Created by lpds on 2017/4/15.
 */
public class DeleteAllRespone extends BaseSqliteEntity {

    boolean isOk;

    public DeleteAllRespone(boolean isOk) {
        this.isOk = isOk;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }
}
