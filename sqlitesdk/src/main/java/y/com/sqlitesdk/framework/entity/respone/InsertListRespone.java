package y.com.sqlitesdk.framework.entity.respone;

import y.com.sqlitesdk.framework.entity.BaseSqliteEntity;

/**
 * Created by lpds on 2017/4/17.
 */
public class InsertListRespone extends BaseSqliteEntity {

    public InsertListRespone(boolean isiInsers) {
        this.isiInsers = isiInsers;
    }

    boolean isiInsers;

    public boolean isiInsers() {
        return isiInsers;
    }

    public InsertListRespone setIsiInsers(boolean isiInsers) {
        this.isiInsers = isiInsers;
        return this;
    }
}
