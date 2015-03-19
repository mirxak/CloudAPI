package com.main.cloudapi.utils;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import javassist.*;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 *
 */
public class JacksonView2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

    boolean prefixJson;

    @Override
    public void setPrefixJson(boolean prefixJson) {
        this.prefixJson = prefixJson;
    }

    private static final Map<String, Class> generatedViews = new ConcurrentHashMap<>();

    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {

        JsonEncoding encoding = getJsonEncoding(outputMessage.getHeaders().getContentType());
        // The following has been deprecated as late as Jackson 2.2 (April 2013);
        // preserved for the time being, for Jackson 2.0/2.1 compatibility.
        //@SuppressWarnings("deprecation")
        JsonWebContext jContex = JsonWebContext.getJsonContext();
        ObjectMapper mapper = null;
        Set<Class<?>> views = null;
        Map<Class<?>, Class> filters = null;
        Set<String> ingnoreFileds = null;
        Set<String> fields2Show = null;
        //log.debug("in {}",this);

        StringBuilder sb = new StringBuilder();

        if (jContex != null) {
            sb.append("JsonContex is detected; ");
            mapper = jContex.getMapper();
            views = jContex.getViews();
            filters = jContex.getFilters();
            fields2Show=jContex.getFields2Show();
            ingnoreFileds = jContex.getFields2Ignore();
        }

        if (mapper == null && jContex != null) {
            sb.append("set default mapper; ");
            mapper = new KJsonMapper();
        } else {
            sb.append("set standart mapper; ");
            mapper = getObjectMapper();
        }

        if (filters != null) {
            for (Map.Entry<Class<?>, Class> f : filters.entrySet()) {
                Class objClass = f.getValue();
                if (objClass == null) {
                    objClass = object.getClass();
                    if (object instanceof Collection) {
                        Collection collection = (Collection) object;
                        if (!collection.isEmpty()) {
                            objClass = collection.iterator().next().getClass();
                        }
                    }
                }
                sb.append("addMixInAnnotations for ").append(object.getClass().getName()).
                        append(" annotated class=").append(f.getKey()).
                        append(" for class=").append(objClass.getName()).append("; ");
                mapper.addMixInAnnotations(objClass, f.getKey());
            }

        }

        ObjectWriter writer = null;

        if(fields2Show!=null&&!fields2Show.isEmpty()){
            mapper.addMixInAnnotations(
                    Object.class, JsonUtils.PropertyFilterMixIn.class);
            FilterProvider newF = new SimpleFilterProvider()
                    .addFilter("filter properties by name",
                            SimpleBeanPropertyFilter.filterOutAllExcept(
                                    fields2Show)
                    );
            writer = mapper.writer(newF);
        }
        else if (ingnoreFileds != null && !ingnoreFileds.isEmpty()) {
            mapper.addMixInAnnotations(
                    Object.class, JsonUtils.PropertyFilterMixIn.class);
            //String[] ignorableFieldNames = { "a", "field2" };
            /*
             FilterProvider filters = new SimpleFilterProvider()
            .addFilter("myFilter", SimpleBeanPropertyFilter
                    .filterOutAllExcept(
                    new HashSet<String>(Arrays.asList(new String[] { "name", "firstName" }))));
             */
//            Set<String> outerFields=new HashSet<>();
//            Map<String,String> innerFields=new HashMap<>();
//            for(String field:ingnoreFileds){
//                if(field.contains(".")){
//                    field.
//                }
//            }

            FilterProvider newF = new SimpleFilterProvider()
                    .addFilter("filter properties by name",
                            SimpleBeanPropertyFilter.serializeAllExcept(
                                    ingnoreFileds)
                    );
            writer = mapper.writer(newF);
        }

        Class targetView;
        if (views != null && !views.isEmpty()) {
//            for(Class<?> v:views){
//                sb.append("set view=").append(v.getName()).append("\n");
//            }
            Set<Class> allV = new LinkedHashSet<>();
            allV.addAll(views);
            String name = JsonUtils.generateName(allV);
            targetView = generatedViews.get(name);
            if (targetView == null) {
                try {
                    targetView = JsonUtils.generateView(name, allV);
                    generatedViews.put(name, targetView);
                } catch (CannotCompileException e) {
                    logger.error("cant generate view for JSON!", e);
                }
            }

        } else {
            sb.append("set view=NULL; ");
            targetView = Object.class;
        }

        JsonGenerator jsonGenerator =
                mapper.getFactory().createGenerator(outputMessage.getBody(), encoding);

        // A workaround for JsonGenerators not applying serialization features
        // https://github.com/FasterXML/jackson-databind/issues/12
        if (this.getObjectMapper().isEnabled(SerializationFeature.INDENT_OUTPUT)) {
            jsonGenerator.useDefaultPrettyPrinter();
        }

        try {
            if (this.prefixJson) {
                jsonGenerator.writeRaw("{} && ");
            }
            if(writer==null) {
                writer = mapper.writer();
            }
//            for (Class<?> view : views) {
//                writer = writer.withView(view);
//            }
            if (targetView != null) {
                writer = writer.withView(targetView);
            }
            writer.writeValue(jsonGenerator, object);

        } catch (JsonProcessingException ex) {
            throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
        }
    }


}

