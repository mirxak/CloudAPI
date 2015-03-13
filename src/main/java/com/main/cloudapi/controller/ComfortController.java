package com.main.cloudapi.controller;

import com.main.cloudapi.api.ComfortControllerI;
import com.main.cloudapi.controller.base.BaseController;
import com.main.cloudapi.entity.Comfort;
import com.main.cloudapi.service.ComfortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by mirxak on 02.02.15.
 */
@Controller
public class ComfortController extends BaseController implements ComfortControllerI {

    @Autowired
    ComfortService comfortService;

    @Override
    public Comfort getComfort(@PathVariable String brandID, @PathVariable String carID, @PathVariable String cID, @PathVariable String id) {
        return comfortService.getById(parseID(id));
    }

    @Override
    public Comfort addComfort(@PathVariable String brandID, @PathVariable String carID, @PathVariable String cID, @RequestBody String json) {
        return comfortService.add(json);
    }

    @Override
    public Comfort editComfort(@PathVariable String brandID, @PathVariable String carID, @PathVariable String cID, @PathVariable String id, @RequestBody String json) {
        return comfortService.edit(parseID(id), json);
    }

    @Override
    public Comfort deleteComfort(@PathVariable String brandID, @PathVariable String carID, @PathVariable String cID, @PathVariable String id) {
        return comfortService.delete(parseID(id));
    }
}
