package org.example.productservice.config.datasource;

import org.example.productservice.config.datasource.enums.DataSourceType;

public class DatabaseContextHolder {
    private static final ThreadLocal<String> holder = new ThreadLocal<>();

    public static void putDataSource(DataSourceType dataSourceType) {
        holder.set(dataSourceType.getType());
    }

    public static String getDataSource() {
        return holder.get();
    }

    public static void set(DataSourceType databaseType) {
        holder.set(databaseType.getType());
    }

    public static void clearDataSource() {
        holder.remove();
    }
}
