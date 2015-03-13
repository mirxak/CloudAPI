package com.main.cloudapi.controller.base;

/**
 * Created by mirxak on 23.01.15.
 */
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Pattern;


public class ApiManager {


    //private static final TreeSet<ApiList> apis = new TreeSet<>();

    private static final Comparator<ApiList> comparator=new Comparator<ApiList>() {
        @Override
        public int compare(ApiList o1, ApiList o2) {
            return o1.compareTo(o2);
        }
    } ;

    private static final Map<String,TreeSet<ApiList>> allApi=new HashMap<>();





    public static void add(String key,String title, String url) {

//        System.out.println("add api to ="+key);
        TreeSet<ApiList> tree=allApi.get(key);
        if(tree==null){
            tree=new TreeSet<>(comparator);
            allApi.put(key,tree);
        }
        ApiList l = new ApiList(title, url);
        tree.add(l);
    }

    private static final Pattern COMPILE = Pattern.compile("/");

    private static String getPath(HttpServletRequest request){
        String path= COMPILE.matcher(request.getServletPath()).replaceFirst("") ;
        if(!path.endsWith("/")){
            path+="/";
        }
        return path;
    }



    public static Set getApi(HttpServletRequest request,String key) {
//        System.out.println("\nget for="+key);
        Set<ApiList> treeSet=allApi.get(key);
        String path=getPath(request);
        if(treeSet==null){
            treeSet= Collections.emptySet();
        }

        for (ApiList l : treeSet) {
            l.addContexPath(path);
        }
        return treeSet;
    }

    public static class ApiList implements Comparable {
        public String url;
        public final String title;
        private boolean ContexAdd;

        public ApiList(String title, String URL) {
            this.title = title;
            this.url = URL;
        }

        @Override
        public boolean equals(Object anObject) {
            if (anObject instanceof ApiList) {
                ApiList l = (ApiList) anObject;
                return this.title.equals(l.title) && this.url.equals(l.url);
            }
            return false;
        }

        @Override
        public String toString() {
            return "title=" + title + " ; url=" + url;

        }

        @Override
        public int compareTo(Object o) {
            ApiList l = (ApiList) o;

            int res = this.url.compareTo(l.url);
            if (res != 0) {
                return res;
            }

            return this.title.compareTo(l.title);
        }

        public void addContexPath(String path) {
            synchronized (this) {
                if (!ContexAdd) {
                    this.url = path + this.url;
                    this.url = this.url.replace("//", "/");
                    ContexAdd = true;
                }
            }
        }
    }
}