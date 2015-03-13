package com.main.cloudapi.utils;

/**
 * Created by mirxak on 23.01.15.
 */

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;

/**
 * Тут делаем DataSouce
 */
public class FabricDataSource {

    public static DataSource createDataSource(String database) {
        if (database != null) {
            database = database.trim().replace(";", "");
            DriverManagerDataSource ds = new DriverManagerDataSource();

            ds.setDriverClassName("org.postgresql.Driver");
            
            ds.setUrl(database);


            return ds;
        }
        throw new RuntimeException("invalid Database database=null");
    }

    public static DataSource createDataSource(String database, String driverClassName) {

        if (database != null) {
            database = database.trim().replace(";", "");
            
            DriverManagerDataSource ds = new DriverManagerDataSource();
            ds.setDriverClassName(driverClassName);
            ds.setUrl(database);
            return ds;
        }
        throw new RuntimeException("invalid Database database=null");
    }

    public static DataSource getNULLDataSource() {
        
        String defaultURL = MainConfig.get("db.default");


        if (defaultURL != null && !defaultURL.isEmpty()) {

            return createDataSource(defaultURL);
        } else {
            
            return createDataSource("NULL");
        }
    }

}