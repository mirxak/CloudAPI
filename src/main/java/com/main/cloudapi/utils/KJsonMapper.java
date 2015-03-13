package com.main.cloudapi.utils;

/**
 * Created by mirxak on 23.01.15.
 */
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

/**
 *
 */
public class KJsonMapper extends ObjectMapper{

    public static interface NULL_view{};


    public KJsonMapper(){
        setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        enable(SerializationFeature.INDENT_OUTPUT);
        configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        configure(MapperFeature.AUTO_DETECT_SETTERS,true);
        configure(MapperFeature.AUTO_DETECT_GETTERS,true);
        disable(MapperFeature.USE_GETTERS_AS_SETTERS);
        configure(JsonParser.Feature.ALLOW_COMMENTS,true);
        configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,false);
        Hibernate4Module hm = new Hibernate4Module();
        hm.configure(Hibernate4Module.Feature.FORCE_LAZY_LOADING,true);
        hm.configure(Hibernate4Module.Feature.USE_TRANSIENT_ANNOTATION,false);
        configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS,true);
        registerModule(hm);
    }


    public KJsonMapper(boolean withModule){
        setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        enable(SerializationFeature.INDENT_OUTPUT);
        configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//        configure(MapperFeature.AUTO_DETECT_FIELDS,false);
        configure(MapperFeature.AUTO_DETECT_SETTERS,true);
        configure(MapperFeature.AUTO_DETECT_GETTERS,true);
        //configure(MapperFeature.)
        disable(MapperFeature.USE_GETTERS_AS_SETTERS);
        //enable(MapperFeature.USE_STATIC_TYPING);
        configure(JsonParser.Feature.ALLOW_COMMENTS,true);
        configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,false);
        configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS,true);

        if(withModule){
            Hibernate4Module hm = new Hibernate4Module();
            hm.configure(Hibernate4Module.Feature.FORCE_LAZY_LOADING,true);
            hm.configure(Hibernate4Module.Feature.USE_TRANSIENT_ANNOTATION,false);
            registerModule(hm);
        }
    }

}
