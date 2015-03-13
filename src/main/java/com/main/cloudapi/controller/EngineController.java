package com.main.cloudapi.controller;

import com.main.cloudapi.api.EngineControllerI;
import com.main.cloudapi.controller.base.BaseController;
import com.main.cloudapi.entity.EngineGearbox;
import com.main.cloudapi.service.EngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by mirxak on 02.02.15.
 */
@Controller
public class EngineController extends BaseController implements EngineControllerI {

    @Autowired
    EngineService engineService;

    @Override
    public EngineGearbox getEngineGearbox(@PathVariable String brandID, @PathVariable String carID, @PathVariable String cID, @PathVariable String id) {
        return engineService.getById(parseID(id));
    }

    @Override
    public EngineGearbox addEngineGearbox(@PathVariable String brandID, @PathVariable String carID, @PathVariable String cID, @RequestBody String json) {
        return engineService.add(json);
    }

    @Override
    public EngineGearbox editEngineGearbox(@PathVariable String brandID, @PathVariable String carID, @PathVariable String cID, @PathVariable String id, @RequestBody String json) {
        return engineService.edit(parseID(id), json);
    }

    @Override
    public EngineGearbox deleteEngineGearbox(@PathVariable String brandID, @PathVariable String carID, @PathVariable String cID, @PathVariable String id) {
        return engineService.delete(parseID(id));
    }
}
