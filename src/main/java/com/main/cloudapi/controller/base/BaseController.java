package com.main.cloudapi.controller.base;

/**
 * Created by mirxak on 23.01.15.
 */
import com.main.cloudapi.constmes.Messages;
import com.main.cloudapi.constmes.ThrowFabric;
import com.main.cloudapi.entity.base.BaseEntity;
import com.main.cloudapi.utils.JsonUtils;
import com.main.cloudapi.utils.MainConfig;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.validator.method.MethodConstraintViolation;
import org.hibernate.validator.method.MethodConstraintViolationException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;
import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Базовый контролер ( формирует список апи)
 */
public class BaseController {

    public static final String ENABLED="1";


    @Autowired(required = false)
    ServletContext context;

    protected static int OFFSET = 0;
    protected static int LIMIT = 5000;

    private DefaultController defaultController;

    @Autowired(required = false)
    public void setDefaultController(DefaultController defaultController) {
        this.defaultController = defaultController;
    }

    protected static Long parseID(String id) {
        Long idL;
        try {
            idL = Long.parseLong(id.trim());
        } catch (Exception e) {

            throw new ThrowFabric.BadRequestException("parse.long.error");
        }
        return idL;
    }

    protected static Long parseID(String id,boolean nullThrow) {
        Long idL;
        try {
            if(id==null||"null".equalsIgnoreCase(id)){
                if(nullThrow){
                    throw new ThrowFabric.BadRequestException("parse.long.error");
                } else{
                    return null;
                }
            }
            idL = Long.parseLong(id.trim());
        } catch (Exception e) {

            throw new ThrowFabric.BadRequestException("parse.long.error");
        }
        return idL;
    }

    public static Set<Long> parseIDS(String ids) {
        try {
            long[] idsLong = JsonUtils.getFromJson(ids, long[].class);
            Set<Long> idL = new HashSet<>();
            idL.addAll(Arrays.asList(ArrayUtils.toObject(idsLong)));
            return idL;
        } catch (Exception e) {
            throw new ThrowFabric.BadRequestException("parse.longs.error");
        }
    }

    @PostConstruct
    protected void apiInit(){
        if(defaultController!=null){
            defaultController.regController(this);
        }

        Method[] met;
        Class C = this.getClass();
        met = C.getMethods();

        String baseMap = "";

        Annotation[] an = C.getDeclaredAnnotations();


        Class[] faces = C.getInterfaces();
        for (Class face : faces) {
            Annotation BaseAn = face.getAnnotation(RequestMapping.class);

            if (BaseAn != null) {
                RequestMapping rqBase = (RequestMapping) BaseAn;
                String[] reqValues = rqBase.value();
                if (reqValues.length > 0) {
                    baseMap = reqValues[0];
                }
            }


            for (Method m : face.getMethods()) {

                Annotation a = m.getAnnotation(RequestMapping.class);
                if (a != null) {
                    String reqVal = "";
                    RequestMapping rqBase = (RequestMapping) a;
                    String[] reqValues = rqBase.value();

                    if (reqValues.length > 0) {
                        reqVal = reqValues[0];
                    }
                    StringBuilder actions = new StringBuilder();
                    for (RequestMethod actionsM : rqBase.method()) {
                        actions.append(actionsM.name()).append(" ");
                    }
                    StringBuilder params = new StringBuilder();
                    for (String param : rqBase.params()) {
                        params.append(params.length() > 0 ? "&" : "").append(param).append("=");
                    }

                    if (params.length() > 0) {
                        params.insert(0, "?");

                    }
                    //if(context!=null)context.getContextPath();
                    String key="";
                    if(defaultController!=null){
                        key=defaultController.toString();
                    }
                    ApiManager.add(key,
//                            m.getName() + " (" + actions.trim() + ")", //title
                            baseMap + '/' + reqVal + params + "(" + actions.toString().trim() + ")",  //url

                            baseMap + '/' + reqVal + params  //url
                    );
                }
            }
        }

        if (faces.length == 0) {
            if (an != null && an.length > 0) {
                baseMap = an[0].toString();
            }
            if (met != null) {
                for (Method m : met) {
                    //if(m.isAccessible()) {
                    StringBuilder anotations = new StringBuilder();

                    for (Annotation a : m.getAnnotations()) {
                        anotations.append(a.toString()).append("--").append(a.annotationType().getName()).append(";  ");
                    }

                    String key="";
                    if(defaultController!=null){
                        key=defaultController.toString();
                    }
                    ApiManager.add( key,
                            m.getName(), //title
                            baseMap + '/' + anotations  //title
                    );
                    // }
                }
            }
        }
    }

    public static final String ENCODING = "UTF-8";


    private Messages.jsMess resolveBindingException(ThrowFabric.BadRequestException e,int status,boolean print) {
        

        if(ThrowFabric.class.isAssignableFrom(e.getClass())){
            
            ThrowFabric ex=(ThrowFabric)e;
            if(ex.getMess()!=null){
                Messages.jsMess from=ex.getMess().getMess();
                Messages.jsMess message = new Messages.jsMess();
                message.success = from.success;
                message.type = from.type;
                message.message =ThrowFabric.getLocaleMessage(from.message);
                message.status=status;
                return message;
            }
        }

        if(print){
            e.printStackTrace();
        }
        String mes = e.getMessage();
        


        if (mes == null) {
            mes = e.getClass().getSimpleName();
        }
        //stream.write(mes.getBytes(ENCODING));
        Messages.jsMess message = new Messages.jsMess();
        // message.status = HttpStatus.BAD_REQUEST.value();
        message.success = false;
        message.type = Messages.jsMess.MessType.System;
        message.message = mes;

        return message;
    }

//    @ExceptionHandler
//    @ResponseBody
//    public void RUNTIME_ALL(AssertionFailure e, HttpServletResponse response){
//        
//        resolveBindingException(e,response);
//
//    }


    @ExceptionHandler
    @ResponseBody
    public <T extends ThrowFabric> Messages.jsMess exceptionHandler(T e, HttpServletResponse response) {
        response.setCharacterEncoding(ENCODING);
        int status=getClassStatus(e);
        response.setStatus(status);
        return resolveBindingException((ThrowFabric.BadRequestException) e,status,false);
    }



    private int getClassStatus(ThrowFabric e){
        int res=HttpStatus.BAD_REQUEST.value();
        try{
            ResponseStatus an = e.getClass().getAnnotation(ResponseStatus.class);
            res=an.value().value();
        }
        catch (Exception ignored){}
        return res;
    }


    private static  Map<Class,Method> methodGetLeafNode=new ConcurrentHashMap<>();
    private static  Map<Class,Method> methodLeafGetName=new ConcurrentHashMap<>();;

    private String getPropPath(Object path){
        String propName="undefined";
        try {
            Method getLeafNode=methodGetLeafNode.get(path.getClass());
            if (getLeafNode== null) {
                getLeafNode = path.getClass().getMethod("getLeafNode");
                methodGetLeafNode.put(path.getClass(),getLeafNode);
            }
            if (getLeafNode != null) {
                Object  leaf = getLeafNode.invoke(path);
                Method leafGetName = methodLeafGetName.get(leaf.getClass());
                if (leafGetName == null) {
                    leafGetName = leaf.getClass().getMethod("getName");
                    methodLeafGetName.put(leaf.getClass(),leafGetName);
                }
                if (leafGetName != null) {
                    propName = (String) leafGetName.invoke(leaf);
                }
            }
        }
        catch (Exception eEX){
        }
        return propName;
    }

    @ExceptionHandler
    @ResponseBody
    public Messages.jsMess validatorExceptionHandler(MethodConstraintViolationException e, HttpServletResponse response) {
        if("1".equals(MainConfig.get("validation.print.stack.trace"))){
        }

        //TODO переписать после выхода 4го спринга (будет поддержка HibernateValidator5 beanValidator 1.1)
        //  String mes = e.getConstraintViolations().toString();
        StringBuilder sb = new StringBuilder();
        Set<MethodConstraintViolation<?>> contrains = e.getConstraintViolations();
        Locale currentLocale =  LocaleContextHolder.getLocale();

        for (MethodConstraintViolation<?> met : contrains) {
            String localizedErrorMessage;
            String template = met.getMessageTemplate();
            if (template.startsWith("{")) {
                Class<? extends Annotation> an = met.getConstraintDescriptor().getAnnotation().annotationType();
                template = an.getSimpleName();
                String propName=getPropPath(met.getPropertyPath());
                String field=JsonUtils.toLOWER_CASE_WITH_UNDERSCORES(propName);
                String[] args={field};

                localizedErrorMessage= ThrowFabric.getLocaleMessage(template,args);
            }
            else{
                localizedErrorMessage=ThrowFabric.getLocaleMessage(template);
            }
            sb.append(localizedErrorMessage).append(";");
        }

        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setCharacterEncoding(ENCODING);

        Messages.jsMess mes = new Messages.jsMess();
        mes.status = HttpStatus.BAD_REQUEST.value();
        mes.success = false;
        mes.type = Messages.jsMess.MessType.System;
        mes.message = sb.toString();
        
        return mes;

    }



    /*

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorDTO processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        return processFieldErrors(fieldErrors);
    }

    private ValidationErrorDTO processFieldErrors(List<FieldError> fieldErrors) {
        ValidationErrorDTO dto = new ValidationErrorDTO();

        for (FieldError fieldError: fieldErrors) {
            String localizedErrorMessage = resolveLocalizedErrorMessage(fieldError);
            dto.addFieldError(fieldError.getField(), localizedErrorMessage);
        }

        return dto;
    }

    private String resolveLocalizedErrorMessage(FieldError fieldError) {
        Locale currentLocale =  LocaleContextHolder.getLocale();
        String localizedErrorMessage = messageSource.getMessage(fieldError, currentLocale);

        //If the message was not found, return the most accurate field error code instead.
        //You can remove this check if you prefer to get the default error message.
        if (localizedErrorMessage.equals(fieldError.getDefaultMessage())) {
            String[] fieldErrorCodes = fieldError.getCodes();
            localizedErrorMessage = fieldErrorCodes[0];
        }

        return localizedErrorMessage;
    }
     */
    private static Object[] emptyArr = new Object[0];

//    @Autowired
//    @Qualifier("messageSource")
//    private MessageSource messageSource;



    @ExceptionHandler(value = javax.validation.ConstraintViolationException.class)
    @ResponseBody
    public Messages.jsMess validatorExceptionHandler(javax.validation.ConstraintViolationException e, HttpServletResponse response) {
        //e.printStackTrace();


        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setCharacterEncoding(ENCODING);

        //TODO переписать после выхода 4го спринга (будет поддержка beanValidator 1.1)
        Set<ConstraintViolation<?>> setC = e.getConstraintViolations();
        StringBuilder sb = new StringBuilder();

        Locale currentLocale =  LocaleContextHolder.getLocale();


        /*
         * for (MethodConstraintViolation<?> met : contrains) {
         String localizedErrorMessage;
         String template = met.getMessageTemplate();
         if (template.startsWith("{")) {
         Class<? extends Annotation> an = met.getConstraintDescriptor().getAnnotation().annotationType();
         template = an.getSimpleName();
         PathImpl path = (PathImpl)met.getPropertyPath();
         String field=JsonUtils.toLOWER_CASE_WITH_UNDERSCORES(path.getLeafNode().getName());
         String[] args={field};
         localizedErrorMessage= messageSource.getMessage(template,args,template+" "+field,currentLocale);
         }
         else{
         localizedErrorMessage= messageSource.getMessage(template,emptyArr,template,currentLocale);
         }
         sb.append(localizedErrorMessage).append(";");
         }
         */

        for (ConstraintViolation<?> contr : setC) {
            contr.getMessageTemplate();
            String localizedErrorMessage;
            String template = contr.getMessageTemplate();
            if (template.startsWith("{")) {
                Class<? extends Annotation> an = contr.getConstraintDescriptor().getAnnotation().annotationType();
                template = an.getSimpleName();
                ///

                // try {
                String propName=getPropPath(contr.getPropertyPath());

                // }
//                catch (ClassCastException eC){
//                    org.hibernate.validator.internal.engine.path.PathImpl pp=(org.hibernate.validator.internal.engine.path.PathImpl) contr.getPropertyPath();
//                }

                String field=JsonUtils.toLOWER_CASE_WITH_UNDERSCORES(propName);
                String[] args={field};
                localizedErrorMessage= ThrowFabric.getLocaleMessage(template,args);
            }
            else{
                localizedErrorMessage= ThrowFabric.getLocaleMessage(template);
            }
            sb.append(localizedErrorMessage).append(";");
//
//            String localizedErrorMessage = messageSource.getMessage(contr.getMessage(),emptyArr,contr.getMessage(), currentLocale);
//
//            sb.append(contr.getLeafBean().getClass().getSimpleName()).append(" ").append(localizedErrorMessage).append("\n");
        }
        Messages.jsMess mes = new Messages.jsMess();
        mes.status = HttpStatus.BAD_REQUEST.value();
        mes.success = false;
        mes.type = Messages.jsMess.MessType.System;
        mes.message = sb.toString();
        
        return mes;
    }


    @ExceptionHandler(org.hibernate.exception.ConstraintViolationException.class)
    @ResponseBody
    public Messages.jsMess validatorExceptionHandler(org.hibernate.exception.ConstraintViolationException e, HttpServletResponse response) {
        // e.printStackTrace();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setCharacterEncoding(ENCODING);

        StringBuilder sb = new StringBuilder();
        sb.append(ExceptionUtils.getStackTrace(e));
        Messages.jsMess mes = new Messages.jsMess();
        mes.status = HttpStatus.BAD_REQUEST.value();
        mes.success = false;
        mes.type = Messages.jsMess.MessType.System;
        mes.message = sb.toString();
        
        return mes;
    }

    @ExceptionHandler(TypeMismatchException.class)
    @ResponseBody
    public Messages.jsMess TypeMismatchExceptionHandler(TypeMismatchException exception, HttpServletResponse response){
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setCharacterEncoding(ENCODING);

        StringBuilder sb = new StringBuilder();
        sb.append(exception.getMessage());
        Messages.jsMess mes = new Messages.jsMess();
        mes.status = HttpStatus.BAD_REQUEST.value();
        mes.success = false;
        mes.type = Messages.jsMess.MessType.System;
        mes.message = sb.toString();
        
        return mes;
    }


    @ExceptionHandler(ThrowFabric.BadRequestException.class)
    @ResponseBody
    public Messages.jsMess RuntimeHandler(ThrowFabric.BadRequestException e, HttpServletResponse response) {
        e.printStackTrace();

        if(ThrowFabric.class.isAssignableFrom(e.getClass())){
            ThrowFabric ex=(ThrowFabric)e;
            if(ex.getMess()!=null){
                return ex.getMess().getMess();
            }
        }

        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setCharacterEncoding(ENCODING);
        Messages.jsMess mes = new Messages.jsMess();
        mes.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        mes.success = false;
        mes.type = Messages.jsMess.MessType.System;
        StackTraceElement[] stack= e.getStackTrace();

        StringBuilder sb=new StringBuilder();
        sb.append(e.getMessage());
        if(stack.length>0){
            sb.append("\n").append(stack[0]);
        }

        Throwable ee = e.getCause();
        while (ee != null) {
            sb.append("\n").append(ee.getMessage());
            stack= ee.getStackTrace();
            sb.append("\n");
            if(stack.length>0){
                sb.append("\n").append(stack[0]);
            }
            ee = ee.getCause();
        }

        mes.message = sb.toString();
        return mes;
    }

    protected <T extends BaseEntity> T getFromSetByID(Long id, Collection<T> set) {
        for (T d : set) {
            if (d.getId().equals(id)) {
                return d;
            }
        }

        return null;
    }

    // org.hibernate.validator.method.MethodConstraintViolationException
    protected <T extends BaseEntity> T getFromSetByID(Long id, Collection<T> set, String thowMessage) {
        T t = getFromSetByID(id, set);
        if (t == null) {
            throwNotFound(thowMessage);
        }
        return t;
    }

    protected void throwNotFound(String mess, Class t) {
        throw new ThrowFabric.BadRequestException("entity.not.exist.message",mess,t);
    }

    protected void throwNotFound(String mess) {
        throw new ThrowFabric.BadRequestException(mess);
    }
}