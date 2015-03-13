package com.main.cloudapi.utils;

/**
 * Created by mirxak on 23.01.15.
 */
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


@EnableAspectJAutoProxy(proxyTargetClass = true)
@Component
public class MainConfig extends PropertyPlaceholderConfigurer
        implements ResourceLoaderAware,
        ApplicationListener<ApplicationEvent> {

    private static boolean needRestart = true;


    private int contextCount;

//    @Bean
//    public ResourceBundleMessageSource getMessageSource() {
//        ResourceBundleMessageSource r = new ResourceBundleMessageSource();
//       // r.getMessage()
//        r.setBasename("messages");
//        return r;
//    }
//    @Bean
//    public ResourceBundle getMessageSource() {
//        ResourceBundle bundle = ResourceBundle.getBundle("messages");
//        return bundle;
//    }


    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {

        if (applicationEvent instanceof ContextRefreshedEvent) {

            logger.debug("pre restart (MainConfig)=" + ((ContextRefreshedEvent) applicationEvent).getApplicationContext().getApplicationName());
            contextCount++;

            String countStr = get("context.count");
            int count = countStr == null ? 1 : Integer.valueOf(countStr);

            if (count - contextCount <= 0) {
                try {
                    restart();
                } catch (Exception e) {
                    logger.error("\nERROR on MainConfig.restart()");
                    e.printStackTrace();
                }
            }
        }


    }


    private static Resource[] locations;

    private static Map<String, String> propertiesMap;
    // Default as in PropertyPlaceholderConfigurer
    private int springSystemPropertiesMode = SYSTEM_PROPERTIES_MODE_OVERRIDE;


    private static MainConfig hookFromStatic;

    public MainConfig() {
        if (hookFromStatic == null) {
            hookFromStatic = this;
        }
//        logger.debug("Bean MainConfig init.");
    }

    @Override
    public void setSystemPropertiesMode(int systemPropertiesMode) {
        super.setSystemPropertiesMode(systemPropertiesMode);
        springSystemPropertiesMode = systemPropertiesMode;
    }


    @Override
    public void setLocations(Resource[] locations) {
        MainConfig.locations = locations;
        super.setLocations(locations);
    }

    @Override
    public void setLocation(Resource location) {
//If a single location is configured, we'll need to add it to our array in
//sposition #1

        locations = new Resource[]{location};
        super.setLocation(location);
    }

    public static Resource[] getLocations() {
        return locations;
    }


    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) throws BeansException {
        super.processProperties(beanFactory, props);
        setProps(props, true);
    }

    public static void reloadProps() {
        if (propertiesMap != null) {
            propertiesMap.clear();
        }
        load();

    }

    public void setProps(Properties properties, boolean clear) {
        if (clear || propertiesMap == null) {
            propertiesMap = new HashMap<>();
        }
        StringBuilder sb = new StringBuilder("XXXXX setProps");

        for (Object key : properties.keySet()) {
            String keyStr = key.toString();
            String valueStr = resolvePlaceholder(keyStr, properties, springSystemPropertiesMode);
            propertiesMap.put(keyStr, valueStr);
            sb.append("\nsetProps--->").append(key).append(" : ").append(valueStr);
        }
        logger.debug(sb.toString());
    }


//    private static final String[] PATH = {"/WEB-INF/main.properties", "/WEB-INF/version.properties", "/WEB-INF/build.properties"};
private static final String[] PATH = {"/WEB-INF/main.properties"};

    private static void load() {

        if (hookFromStatic != null) {
            Resource r = null;
            boolean loacationsEmpty=locations==null||locations.length==0;
            for (int i = 0; i < PATH.length; i++) {


                if (loacationsEmpty) {
                    try {
                        r = hookFromStatic.getResource(PATH[i]);
                    }
                    catch (Exception e){
                        throw new RuntimeException(e);
                    }
                } else {
                    if(locations.length>i) {
                        r = locations[i];
                    }
                    else{
                        break;
                    }
                }

                if (r != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("\n==>>>").append(r);
                    hookFromStatic.setLocation(r);
                    try {
                        Properties p = hookFromStatic.mergeProperties();
                        hookFromStatic.setProps(p,i==0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        InputStream in = r.getInputStream();

                        BufferedReader in2 = new BufferedReader(new InputStreamReader(in));
                        String line;
                        while ((line = in2.readLine()) != null) {
                            sb.append("\n==>>>").append(line);
                        }
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void restart() {
        synchronized (MainConfig.class) {
            if (needRestart) {
                needRestart = false;
                String auto_rest = get("auto.restart");
                if (auto_rest != null && "1".equals(auto_rest)) {
                    String sep = System.getProperty("file.separator");
                    Resource r = null;
                    if (locations == null || locations.length == 0) {
                        r = hookFromStatic.getResource(PATH[0]);
                    } else {
                        r = locations[0];
                    }
                    if (r == null) {
                    }
                    String pathE = "";
                    try {
                        pathE = r.getFile().getPath() + pathE;
                    } catch (Exception e) {
                        //e.printStackTrace();
                        return;
                    }
                    pathE = pathE.substring(0, pathE.lastIndexOf(sep));
                    pathE += sep + "restart";

                    File file = new File(pathE);
                    if (!file.exists()) {
                        try {
                            String filepath = file.getAbsolutePath();
                            if (file.createNewFile()) {

                                String serverPath = sep + "glassfish" + sep + "domains";

                                String pathToServ = filepath.substring(0, filepath.indexOf(serverPath));
                                if ("/".equals(sep)) {
                                    pathToServ += sep + "bin" + "/asadmin ";
                                } else {
                                    pathToServ += sep + "bin" + "\\asadmin.bat ";
                                }
                                // String[] path=file.getAbsolutePath().split(System.getProperty())
                                //
                                pathToServ += " restart-domain domain1";
                                try {
                                    Runtime.getRuntime().exec(pathToServ);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public static String get(String name) {
        if ((locations == null || locations.length == 0) && (propertiesMap == null || propertiesMap.isEmpty())) {
            load();
        }

        try {
            assert propertiesMap != null;
            return propertiesMap.get(name);
        } catch (Exception e) {
            throw new RuntimeException("Can`t read property \"" + name + "\"", e);
        }


    }


    private ResourceLoader resourceLoader;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Resource getResource(String location) {
        if (resourceLoader != null) {
            return resourceLoader.getResource(location);
        } else {
            return null;
        }
    }
}
