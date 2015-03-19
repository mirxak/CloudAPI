package com.main.cloudapi.utils;

import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 */
public class JsonWebContext {

    private static final ThreadLocal<JsonWebContext> tlv = new ThreadLocal<>();
    private KJsonMapper mapper;
    private final Map<Class<?>, Class> filters = new HashMap<>();
    private final Set<Class<?>> views = new HashSet<>();

    private final Set<String> fieldsToIgnore=new HashSet<>();
    private final Set<String> fieldsToShow=new HashSet<>();

    private static AtomicLong counter=new AtomicLong(0);

    public JsonWebContext() {
        counter.incrementAndGet();
    }

    public KJsonMapper getMapper() {
        return mapper;
    }

    public Map<Class<?>, Class> getFilters() {
        return filters;
    }

    public void setFilters(Map<Class<?>, Class> filters) {
        this.filters.putAll(filters);
    }

    public Set<Class<?>> getViews() {
        return views;
    }

    public void addViews(Set<Class<?>> views) {
        this.views.addAll(views);
    }

    public void addView(Class<?> view) {
        this.views.add(view);
    }

    @Deprecated
    public void addFilter(Class<?> filter, Class toClass) {
        this.filters.put(filter, toClass);
    }

    public static void addFields2Ignore(Set<String> fields){
        JsonWebContext wc=getContex();
        if(fields!=null)
            wc.fieldsToIgnore.addAll(fields);
    }

    public Set<String> getFields2Ignore(){
        JsonWebContext wc=getContex();
        return  wc.fieldsToIgnore;
    }



    public static void addFields2Show(Set<String> fields){
        JsonWebContext wc=getContex();
        if(fields!=null)
            wc.fieldsToShow.addAll(fields);
    }

    public Set<String> getFields2Show(){
        JsonWebContext wc=getContex();
        return  wc.fieldsToShow;
    }


    //методы для добавления фильтров и вьюх
    @Deprecated
    public static void addMapperFilter(Class<?> filter) {
        addMapperFilter(filter, null);
    }

    public static void addMapperFilter(Class<?> filter, Class toClass) {
        JsonWebContext wc = getContex();
        Assert.notNull(filter, "filter can`t be null");

        wc.addFilter(filter, toClass);
    }

    public static void addMapperView(Class<?> view) {
        JsonWebContext wc = getContex();

        Assert.notNull(view, "view can`t be null");
        wc.addView(view);
    }

    public static void setObjectMapper(KJsonMapper mapper) {
        JsonWebContext wc = getContex();
        wc.mapper = mapper;
    }

    public static void clearViews() {
        JsonWebContext wc = getContex();
        wc.views.clear();
    }


    @Deprecated
    public static void clearFilters() {
        JsonWebContext wc = getContex();
        wc.filters.clear();
    }

//    public static void setMapperView(Class<?> view) {
//        clearViews();
//        addMapperView(view);
//    }

    public static void removeView(Class<?> view) {
        JsonWebContext wc = getContex();

        wc.views.remove(view);
    }


    public static void removeAllViews() {
        JsonWebContext wc = getContex();

        wc.views.clear();
    }

    private static JsonWebContext getContex() {
        JsonWebContext wc = tlv.get();
        if (wc == null) {
            wc = new JsonWebContext();
            tlv.set(wc);
        }
        return wc;
    }

    public static JsonWebContext getJsonContext() {
        return tlv.get();
    }


    //очистка контекста
    public static void clearKeys() {
        tlv.remove();
    }

    public static void clear() {
        tlv.remove();
        tlv.set(null);
    }

    @Override
    public String toString() {
        return JsonUtils.getJson(this,false);
    }
}

