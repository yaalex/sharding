package com.test.payments.sharded.router;

import com.test.payments.sharded.datasource.ShardingDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.sql.DataSource;

@Component
public class ConsistentHashRouter implements DataSourceRouter {

    private final SortedMap<Long, ShardingDataSource> ring = new TreeMap<>();

    private RouteFunction routeFunction;

    @Autowired
    public ConsistentHashRouter(Collection<DataSource> dataSources, RouteFunction routeFunction) {
        this.routeFunction = routeFunction;
        if (dataSources != null) {
            for (DataSource dataSource : dataSources) {
                addDataSource(dataSource);
            }
        }
    }

    @Override
    // TODO datasource wrapper
    public void addDataSource(DataSource dataSource) {
        try {
            ring.put(routeFunction.getValue(dataSource.getConnection().getMetaData().getURL()), new ShardingDataSource(dataSource));
        } catch (SQLException e) {
            //
        }
    }

    @Override
    public void removeDataSource(DataSource DataSource) {
        //TODO
    }

    @Override
    public ShardingDataSource routeDataSource(String objectKey) {
        if (ring.isEmpty()) {
            return null;
        }
        Long hashVal = routeFunction.getValue(objectKey);
        SortedMap<Long, ShardingDataSource> tailMap = ring.tailMap(hashVal);
        Long dataSourceKey = !tailMap.isEmpty() ? tailMap.firstKey() : ring.firstKey();
        return ring.get(dataSourceKey);
    }

    @Override
    public Collection<ShardingDataSource> getAllDataSources() {
        return ring.values();
    }

}
