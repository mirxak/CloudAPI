package com.main.cloudapi.constmes;

/**
 * Created by mirxak on 23.01.15.
 */

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.Locale;

/**
 * Exceptions для заголовков
 */
public class ThrowFabric extends RuntimeException {

    protected static MessageSource messageSource;

    public static void setMessageSource(MessageSource messageSource) {
        ThrowFabric.messageSource = messageSource;
    }


    private final static Locale RU=new Locale("ru");
    private static final Object[] EMPTY_ARR={};

    public static String getLocaleMessage(String code,Object ... params){
        if(ThrowFabric.messageSource==null){
            return code+"\n"+ Arrays.toString(params);
        }
        else{
            Object[] nP=null;
            if(params!=null){
                nP=new Object[params.length];
                for(int i=0;i<params.length;i++){
                    if(params[i]!=null){
                        String pS=params[i].toString();
//                        logger.trace("search alias for={}",pS);
                        if(pS.length()<200){
                            String defMessage=messageSource.getMessage(pS,EMPTY_ARR,pS,RU);
                            nP[i]= messageSource.getMessage(pS,EMPTY_ARR,defMessage,LocaleContextHolder.getLocale());
                        }
                        else{
                            nP[i]=pS;
                        }
                    }
                    else{
                        nP[i]=null;
                    }
                }
            }

            //RU
            String defMessage=messageSource.getMessage(code,nP==null?EMPTY_ARR:nP,code,RU);
            return messageSource.getMessage(code,nP==null?EMPTY_ARR:nP,defMessage,LocaleContextHolder.getLocale());
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class BadRequestException extends ThrowFabric {
        public BadRequestException() {
        }

        public BadRequestException(String mess, Object... params) {
            super(mess, params);
            
        }

        public BadRequestException(String mess) {
            super(mess);
            
        }

        @Deprecated
        public BadRequestException(Messages mess) {
            super(mess);
            
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class ResourceNotFoundException extends ThrowFabric {
        public ResourceNotFoundException() {
        }

        public ResourceNotFoundException(String mess, Object... params) {
            super(mess, params);
            
        }

        @Deprecated
        public ResourceNotFoundException(Messages mess) {
            super(mess);
            
        }
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public static class NotAcceptableException extends ThrowFabric {
        public NotAcceptableException() {
        }

        public NotAcceptableException(String mess, Object... params) {
            super(mess, params);
            
        }

        public NotAcceptableException(Messages mess) {
            super(mess);
            
        }
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public static class FileTypeUnsupported extends ThrowFabric {
        public FileTypeUnsupported() {
        }

        public FileTypeUnsupported(String mess, Object... params) {
            super(mess, params);
            
        }

        @Deprecated
        public FileTypeUnsupported(Messages mess) {
            super(mess);
            
        }
    }


    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public static class ServiceUnavailable extends ThrowFabric {
        public ServiceUnavailable() {
        }

        public ServiceUnavailable(String mess, Object... params) {
            super(mess, params);
            
        }

        @Deprecated
        public ServiceUnavailable(Messages mess) {
            super(mess);
            
        }

    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    public static class ForbiddenException extends ThrowFabric {
        public ForbiddenException() {
        }

        public ForbiddenException(String mess, Object... params) {
            super(mess, params);
            
        }

        @Deprecated
        public ForbiddenException(Messages mess) {
            super(mess);
            
        }
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public static class UnAuthorizedException extends ThrowFabric {
        public UnAuthorizedException() {
        }

        public UnAuthorizedException(String mess, Object... params) {
            super(mess, params);
            
        }

        @Deprecated
        public UnAuthorizedException(Messages mess) {
            super(mess);
            
        }
    }

    @ResponseStatus(HttpStatus.LOCKED)
    public static class LockedException extends ThrowFabric {
        public LockedException() {
        }

        public LockedException(String mess, Object... params) {
            super(mess, params);
            
        }

        @Deprecated
        public LockedException(Messages mess) {
            super(mess);
            
        }
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public static class UnprocessableEntityException extends ThrowFabric {
        public UnprocessableEntityException() {
        }

        public UnprocessableEntityException(String mess, Object... params) {
            super(mess, params);
            
        }

        @Deprecated
        public UnprocessableEntityException(Messages mess) {
            super(mess);
            
        }
    }


    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public static class I_AM_A_TEAPOT extends ThrowFabric {
        public I_AM_A_TEAPOT() {
        }

        public I_AM_A_TEAPOT(String mess, Object... params) {
            super(mess, params);
            
        }

    }

    @ResponseStatus(HttpStatus.NOT_MODIFIED)
    public static class NOT_MODIFIED extends ThrowFabric {
        public NOT_MODIFIED() {
        }

        public NOT_MODIFIED(String mess, Object... params) {
            super(mess, params);
            
        }

    }


    public ThrowFabric() {
    }

    @Deprecated
    public ThrowFabric(Messages mess) {
        this.mess = mess;
        
    }

    private Messages mess;

    public Messages getMess() {
        return mess;
    }

    public ThrowFabric(String mess, Object... params) {
        super(messageSource == null ? mess : ThrowFabric.getLocaleMessage(mess,params));
    }


}