package com.test.payments.sharded.router;

import com.test.payments.sharded.datasource.ShardingDataSource;

import java.sql.SQLException;
import java.util.Collection;

import javax.sql.DataSource;

public interface DataSourceRouter {

    /**
     * add datasource
     */
    public void addDataSource(DataSource DataSource) throws SQLException;

    /**
     * remove datasource
     */
    public void removeDataSource(DataSource DataSource);

    /**
     * with a specified key, route the nearest DataSource instance
     * @param objectKey
     *            the object key to find a nearest DataSource
     * @return
     */
    ShardingDataSource routeDataSource(String objectKey);

    Collection<ShardingDataSource> getAllDataSources();

}
