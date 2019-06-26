package com.test.payments.sharded.router;

import com.test.payments.sharded.datasource.ShardingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.HashMap;

@Component
public class OrderNumberRouter implements DataSourceRouter {

	private final HashMap<Long, ShardingDataSource> map = new HashMap<>();
	private final RouteFunction routeFunction;

	@Autowired
	public OrderNumberRouter(Collection<DataSource> dataSources) {
		if (dataSources != null) {
			for (DataSource dataSource : dataSources) {
				addDataSource(dataSource);
			}
		}
		this.routeFunction = new HashCodeFunction();
	}

	@Override
	public void addDataSource(DataSource dataSource) {
		ShardingDataSource source = new ShardingDataSource(dataSource);
		synchronized (map) {
			long count = map.size();
			map.put(count, source);
		}
	}

	@Override
	public synchronized void removeDataSource(DataSource DataSource) {
		throw new RuntimeException("Operation is not supported for non-consistent router");
	}

	@Override
	public ShardingDataSource routeDataSource(String objectKey) {
		return map.get(routeFunction.getValue(objectKey));
	}

	@Override
	public Collection<ShardingDataSource> getAllDataSources() {
		return map.values();
	}
}
