package org.example.productservice.config.datasource;

import org.example.productservice.config.datasource.enums.DataSourceType;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class RoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected String determineCurrentLookupKey() {
        if (DatabaseContextHolder.getDataSource() != null) {
            return DatabaseContextHolder.getDataSource();
        }
        return DataSourceType.MASTER.getType();

    }
}
