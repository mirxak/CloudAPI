package com.main.cloudapi.controller;

import com.main.cloudapi.api.SafetyControllerI;
import com.main.cloudapi.controller.base.BaseController;
import com.main.cloudapi.entity.Safety;
import com.main.cloudapi.service.SafetyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by mirxak on 02.02.15.
 */
@Controller
public class SafetyController extends BaseController implements SafetyControllerI {

    @Autowired
    SafetyService safetyService;

    @Override
    public Safety getSafety(@PathVariable String brandID, @PathVariable String carID, @PathVariable String cID, @PathVariable String id) {
        return safetyService.getById(parseID(id));
    }

    @Override
    public Safety addSafety(@PathVariable String brandID, @PathVariable String carID, @PathVariable String cID, @RequestBody String json) {
        return safetyService.add(json);
    }

    @Override
    public Safety editSafety(@PathVariable String brandID, @PathVariable String carID, @PathVariable String cID, @PathVariable String id, @RequestBody String json) {
        return safetyService.edit(parseID(id), json);
    }

    @Override
    public Safety deleteSafety(@PathVariable String brandID, @PathVariable String carID, @PathVariable String cID, @PathVariable String id) {
        return safetyService.delete(parseID(id));
    }
}
