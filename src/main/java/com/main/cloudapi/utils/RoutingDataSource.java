package com.main.cloudapi.utils;

/**
 * Created by mirxak on 23.01.15.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


/**
 *
 */
public class RoutingDataSource extends AbstractRoutingDataSource {

    private static final String NULL_DS_KEY="NULL";
  /*  @Autowired
    private RestServise restServise;*/


    private Map targetDataSources = new HashMap();


    @Override
    public void setTargetDataSources(Map targetDataSources) {
        this.targetDataSources = targetDataSources;
    }

    @PreDestroy
    public void destroy(){
        for(Object DS:targetDataSources.values() ){
            DataSource dataSource = (DataSource)DS;
            try {
                dataSource.getConnection().close();
            } catch (SQLException ignored) {
            }
        }
    }

    @Override
    @SuppressWarnings({"unchecked", "ConstantOnLeftSideOfComparison"})
    protected DataSource determineTargetDataSource() {
        Object lookupKey = determineCurrentLookupKey();
        if(lookupKey==null){
            lookupKey=NULL_DS_KEY;
        }
        DataSource dataSource = (DataSource)this.targetDataSources.get(lookupKey);
        if (dataSource == null) {
            if(NULL_DS_KEY==lookupKey){
                dataSource=FabricDataSource.getNULLDataSource();
            }
            else {
//                String URl=memClient.get((String)lookupKey);
//                if(URl==null||URl.isEmpty()){
//
//                    URl=callBackService.get("get-database/"+lookupKey);
//                }
//
//                dataSource=FabricDataSource.createDataSource(URl);
            }
            targetDataSources.put(lookupKey,dataSource);
        }
        return dataSource;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return RoutingContextHolder.getKey();
    }

    @Override
    public void afterPropertiesSet() {
        // do nothing
        // overridden to avoid datasource validation error by Spring
    }

}
