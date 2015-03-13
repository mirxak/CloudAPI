package com.main.cloudapi.utils;

/**
 * Created by mirxak on 23.01.15.
 */
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import javassist.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

public class JsonUtils {

    private static final ObjectMapper mapper;
    private static final ObjectMapper withOutNULLmapper;
    private static final ObjectMapper withOutLazy;

    private static final ObjectMapper readAllWithOutLazy;
    private static final ObjectMapper readAll;
    private static final ObjectMapper update;

    static {


        mapper = new KJsonMapper();
        update = new KJsonMapper(false);

        withOutNULLmapper = new KJsonMapper();
        withOutNULLmapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, true);
        withOutNULLmapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        withOutNULLmapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        withOutLazy = new KJsonMapper();

        readAllWithOutLazy = new KJsonMapper(false);
        readAllWithOutLazy.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);

        readAll = new KJsonMapper();
        readAll.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);


    }

    //region getJson/getJsonFull
    public static String getJson(Object o) throws JsonProcessingException {
        return mapper.writerWithView(Object.class).writeValueAsString(o);
    }

    public static String getJson(Object o, boolean isThrow) {
        try {
            return mapper.writerWithView(Object.class).writeValueAsString(o);
        } catch (JsonProcessingException e) {
            if (isThrow) {
                throw new RuntimeException("json.serialise.error",e);
            } else {

                return "{}";
            }
        }
    }


    public static String getJson(Object o, Class view, boolean isThrow) {
        return getJson(o, Collections.<Class>singleton(view), isThrow);
    }

    public static String getJson(Object o, Set<Class> views, boolean isThrow){
        try {
            Class v = getView(views);
            return mapper.writerWithView(v).writeValueAsString(o);
        } catch (JsonProcessingException e) {
            if (isThrow) {
                throw new RuntimeException("json.serialise.error.view");
            } else {
                return "{}";
            }
        }
    }

    public static String getJsonFull(Object o, boolean isThrow) {
        try {
            return readAll.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            if (isThrow) {
                throw new RuntimeException("json.serialise.error");
            } else {

                return "{}";
            }
        }

    }


    public static String getJsonFullWithOutLazy(Object o, boolean isThrow) {
        try {
            return readAllWithOutLazy.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            if (isThrow) {
                throw new RuntimeException("json.serialise.error");
            } else {

                return "{}";
            }
        }

    }
    //endregion

    //region getJsonWithOut~
    public static String getJsonWithOutLazy(Object o) throws JsonProcessingException {
        return withOutLazy.writerWithView(Object.class).writeValueAsString(o);
    }

    public static String getJsonWithOutNULL(Object o) throws JsonProcessingException {
        return withOutNULLmapper.writerWithView(Object.class).writeValueAsString(o);
    }

    public static String getJsonWithOutNULL(Object o, boolean isThrow) {
        try {
            return withOutNULLmapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            if (isThrow) {
                throw new RuntimeException("json.serialise.error", e);
            } else {

                return "{}";
            }
        }
    }
    //endregion

    //region getFromJson/getFromJsonFull
    public static <T> T getFromJsonFull(String json, Class<T> expectedClass, boolean isThrow) {
        try {
            return readAll.readValue(json, expectedClass);
        } catch (Exception e) {
            if (isThrow) {
                throw new RuntimeException("json.parse.error");
            } else {

                return null;
            }
        }

    }

    public static <T> T getFromJson(String json, Class<T> expectedClass) throws IOException {

        return mapper.readValue(json, expectedClass);
    }

    public static <T> T getFromJson(String json, Class<T> expectedClass, boolean isThrow) {
        try {
            return mapper.readValue(json, expectedClass);
        } catch (Exception e) {
            if (isThrow) {
                throw new RuntimeException("json.parse.error");
            } else {

                return null;
            }
        }

    }

    public static <T> T getFromJson(String json, Class<T> expectedClass, Set<Class> views, boolean isThrow) {
        Class v = getView(views);
        try {
            ObjectReader reader = mapper.readerWithView(v);
            reader = reader.withType(expectedClass);
            return reader.readValue(json);
        } catch (Exception e) {
            if (isThrow) {
                throw new RuntimeException("json.parse.error");
            } else {

                return null;
            }
        }
    }

    public static <T> T getFromJson(String json, Class<T> expectedClass, Set<Class> views) {
        return getFromJson(json, expectedClass, views, false);
    }
    //endregion

    //region getList/getListFull
    public static <T> List<T> getList(String json, Class T) throws IOException {
        return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, T));
    }

    public static <T> List<T> getList(String json, Class T, Set<Class> view, boolean isThrown) {

        Class v = getView(view);
        try {
            //  mapper.readerWithView(v).readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, T));
            ObjectReader reader = mapper.readerWithView(v);
            reader = reader.withType(mapper.getTypeFactory().constructCollectionType(List.class, T));
            // MappingIterator<T> result =
            return reader.readValue(json);
        } catch (Exception e) {
            if (isThrown) {
                throw new RuntimeException("json.parse.error.list");
            } else {

                return Collections.emptyList();
            }
        }
    }


    public static <T> List<T> getList(String json, Class T, Set<Class> view) {
        return getList(json, T, view, false);
    }


    public static <T> List<T> getList(String json, Class T, boolean isThrown) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, T));
        } catch (Exception e) {
            if (isThrown) {
                throw new RuntimeException("json.parse.error.list");
            } else {

                return Collections.emptyList();
            }

        }
    }

    public static <T> List<T> getListFull(String json, Class<T> expectedClass, boolean isThrow) {
        try {
            return readAll.readValue(json, readAll.getTypeFactory().constructCollectionType(List.class, expectedClass));
        } catch (Exception e) {
            if (isThrow) {
                throw new RuntimeException("json.parse.error.list");
            } else {

                return Collections.emptyList();
            }

        }
    }

    public static <K,V> Map<K,V> getMap(String json,Class<K> keyClass, Class<V> valueClass, boolean isThrow){
        try {
            return readAll.readValue(json, readAll.getTypeFactory().constructMapType(Map.class, keyClass, valueClass));
        } catch (Exception e) {
            if (isThrow) {
                throw new RuntimeException("json.parse.error.list");
            } else {

                return new HashMap<>();
            }

        }
    }
    //endregion

    //region getSet
    public static <T> Set<T> getSet(String json, Class T, boolean isThrown) {
        try {
            if(StringUtils.isBlank(json)&&!isThrown){
                return Collections.emptySet();
            }
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(Set.class, T));
        } catch (Exception e) {
            if (isThrown) {
                throw new RuntimeException("json.parse.error.list");
            } else {

                return Collections.emptySet();
            }

        }
    }
    //endregion

    //region update
    public static <T> T update(String json, T updateEntity) throws IOException {
        ObjectReader updater = update.readerForUpdating(updateEntity);
        updater = updater.withView(Object.class);
        return updater.readValue(json);
    }

    public static <T> T update(String json, T updateEntity, boolean isThrow) {
        ObjectReader updater = update.readerForUpdating(updateEntity);
        updater = updater.withView(Object.class);
        try {
            return updater.readValue(json);
        } catch (IOException e) {
            if (isThrow) {
                throw new RuntimeException("error.json.update.class");
            } else {
                return updateEntity;
            }
        }
    }

    public static <T> T update(String json, T updateEntity, Set<Class> view) throws IOException {
        Class v = getView(view);
        ObjectReader updater = update.readerForUpdating(updateEntity);
        updater = updater.withView(v);

        return updater.readValue(json);
    }

    public static <T> T update(String json, T updateEntity, Set<Class> view, boolean isThrow) {
        Class v = getView(view);
        ObjectReader updater = update.readerForUpdating(updateEntity);
        updater = updater.withView(v);

        try {
            return updater.readValue(json);
        } catch (IOException e) {
            if (isThrow) {
                throw new RuntimeException("error during updating " + updateEntity.getClass().getSimpleName() + " from json! value=" + json + "\n" +
                        e.getMessage());
            } else {
                return updateEntity;
            }
        }
    }


    public static JsonNode readTree(String json,boolean thrown){
        try {
            if (json != null) {
                return mapper.readTree(json);
            } else {
                return null;
            }
        } catch (Exception e) {
            if (thrown) {
                throw new RuntimeException("json.parse.error");
            } else {
                return null;
            }
        }

    }





    @JsonFilter("filter properties by name")
    public static class PropertyFilterMixIn {
    }

    public static <T> T updateProtected(String json, T updateEntity, Set<Class> view, boolean isThrow) {

        Class v = getView(view);
        ObjectReader updater = mapper.readerForUpdating(updateEntity);
        updater = updater.withView(v);
        try {
            return updater.readValue(json);
        } catch (IOException e) {
            if (isThrow) {
                throw new RuntimeException("error during updating " + updateEntity.getClass().getSimpleName() + " from json! value=" + json + "\n" +
                        e.getMessage());
            } else {
                return updateEntity;
            }
        }
    }
    //endregion

    //region View generator
    private static Class getView(Set<Class> views) {
        Class t = Object.class;
        if (views != null && !views.isEmpty()) {

            String generateName = generateName(views);


            t = JsonUtils.views.get(generateName);
            if (t == null) {
                try {
                    t = generateView(generateName, views);
                } catch (CannotCompileException e) {

                }
            }
        }
        return t;
    }

    private static final Lock lock = new ReentrantLock();

    private static HashMap<String, Class> views = new HashMap<>();

    public static Class generateView(String name, Set<Class> classList) throws CannotCompileException {

        Class result = null;

        lock.lock();

        try {

            try {
                result = Class.forName(name);
            } catch (ClassNotFoundException ignored) {
            }

            try {
                if (result == null) {
                    ClassPool pool = ClassPool.getDefault();
                    CtClass newC = pool.makeClass(name);
                    newC.defrost();
                    for (Class c : classList) {

                        try {

                            newC.addInterface(resolveCtClass(c));

                        } catch (NotFoundException e) {
                            e.printStackTrace();

                        }
                    }
                    result = newC.toClass();
                    views.put(name, result);
                }
            } catch (Exception e) {
                result = Object.class;
            }

        } finally {
            lock.unlock();
        }
        return result;
    }

    private static final Pattern NAME_REPLACE = Pattern.compile("[\\.$]");

    public static String generateName(Set<Class> classList) {
        String name;
        StringBuilder sb = new StringBuilder();
        for (Class c : classList) {
            sb.append(NAME_REPLACE.matcher(c.getSimpleName()).replaceAll("_")).append("_");
        }
        name = sb.toString();

        return name;
    }

    private static CtClass resolveCtClass(Class clazz) throws NotFoundException {

        ClassPool pool = ClassPool.getDefault();
        pool.insertClassPath(new ClassClassPath(clazz));
        return pool.get(clazz.getName());
    }
    //endregion

    private static PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy strategy = new PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy();

    public static String toLOWER_CASE_WITH_UNDERSCORES(String s) {
        return strategy.translate(s);
    }

}