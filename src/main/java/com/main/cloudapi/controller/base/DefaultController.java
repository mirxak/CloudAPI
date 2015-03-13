package com.main.cloudapi.controller.base;

/**
 * Created by mirxak on 23.01.15.
 */

import com.main.cloudapi.constmes.Messages;
import com.main.cloudapi.entity.base.BaseEntity;
import com.main.cloudapi.utils.MainConfig;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 */
@Controller
public class DefaultController {

    @RequestMapping
    @ResponseBody
    public Messages.jsMess unmappedRequest(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return new Messages.jsMess(false,"Not found", Messages.jsMess.MessType.System, HttpStatus.NOT_FOUND.value());
    }

    @RequestMapping("api")
    @ResponseBody
    public Set getAPI(HttpServletRequest httpServletRequest){
        return ApiManager.getApi(httpServletRequest,this.toString());
    }


    String[] propsVersion={"application.version","application.version2",
            "git.commit.id","git.commit.user.name","git.build.user.name",
            "git.build.time","git.branch","git.commit.message.full"


    };
    Map<String,String> mapVersion= new LinkedHashMap<>();

    @RequestMapping(value = "version",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,String> getVersion(){

        if(mapVersion.isEmpty()) {
            for (String p : propsVersion) {
                mapVersion.put(p, MainConfig.get(p));
            }
        }
//        application.version = ${build.number}
        return mapVersion;
    }

    @RequestMapping("reload-prop")
    @ResponseBody
    public void reloadP(){
        MainConfig.reloadProps();
    }

    private final Set<BaseEntity> store=new HashSet<>();

    public void addEntity(BaseEntity baseEntity){
        if(baseEntity!=null){
            baseEntity.setId(null);
            store.add(baseEntity);
        }
    }



    private final Set<BaseController> controllers=new HashSet<>();
    public void regController(BaseController controller){
        controllers.add(controller);
    }
    public Set<BaseController> getControllers() {
        return controllers;
    }
}
