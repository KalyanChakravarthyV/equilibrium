package in.vadlakonda.equilibrium.init;

import org.apache.log4j.Logger;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AppInitiliazer {

    private static final org.apache.log4j.Logger log = Logger.getLogger(AppInitiliazer.class);
    private static URLClassLoader classLoader;
    private static File appRoot;

    public static File init() {
        log.info("Initializing");

        appRoot = new File(getAppRoot(), "ClassLoader/Equilibrium");

        File jarRoot = new File(appRoot, "Java");
        File webRoot = new File(appRoot, "Web");

        List<URL> urlList = new ArrayList<URL>();

        for (File f : jarRoot.listFiles()) {
            try {
                urlList.add(f.toPath().toUri().toURL());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        try {
            urlList.add(webRoot.toPath().toUri().toURL());
//            for (File f : webRoot.listFiles()) {
//                urlList.add(f.toPath().toUri().toURL());
//            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        classLoader = new

                URLClassLoader(urlList.toArray(new URL[]{
        }));


        log.info("Initialized");

        return appRoot;

    }

    public static URLClassLoader getAppClassLoader() {

        if (classLoader == null) init();


        return classLoader;

    }

    private static String getAppRoot() {

        //PropertiesLookup.getProperties(PropertiesLookup.WEB_PROPERTIES)

        try {
            Class<?> propsLookup = Class.forName("com.tririga.app.PropertiesLookup");

            String webPropertiesName = (String) propsLookup.getField("WEB_PROPERTIES").get(propsLookup);

            Properties webProperties = (Properties) propsLookup.getMethod("getProperties", String.class).invoke(propsLookup, webPropertiesName);
            log.info("webProperties:" + webProperties);

            if (webProperties.isEmpty()) return ".";

            return webProperties.getProperty("FileSystemRoot");

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return ".";
    }

}
