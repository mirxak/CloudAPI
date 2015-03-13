package com.main.cloudapi.controller;

import com.main.cloudapi.api.BodyControllerI;
import com.main.cloudapi.controller.base.BaseController;
import com.main.cloudapi.entity.Body;
import com.main.cloudapi.entity.Brand;
import com.main.cloudapi.service.BodyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by mirxak on 23.01.15.
 */
@Controller
public class BodyController extends BaseController implements BodyControllerI {

    @Autowired
    BodyService bodyService;

    @Override
    public Body getBody(@PathVariable String brandID, @PathVariable String carID, @PathVariable String cID, @PathVariable String id) {
        return bodyService.getById(parseID(id));
    }

    @Override
    public Body addBody(@PathVariable String brandID, @PathVariable String carID, @PathVariable String cID, @RequestBody String json) {
        return bodyService.add(json);
    }

    @Override
    public Body editBody(@PathVariable String brandID, @PathVariable String carID, @PathVariable String cID, @PathVariable String id, @RequestBody String json) {
        return bodyService.edit(parseID(id), json);
    }

    @Override
    public Body deleteBody(@PathVariable String brandID, @PathVariable String carID, @PathVariable String cID, @PathVariable String id) {
        return bodyService.delete(parseID(id));
    }
}
