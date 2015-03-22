package com.main.cloudapi.controller;

import com.main.cloudapi.api.PriceCoefControllerI;
import com.main.cloudapi.controller.base.BaseController;
import com.main.cloudapi.entity.PriceCoef;
import com.main.cloudapi.service.PriceCoefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Created by mirxak on 22.03.15.
 */
@Controller
public class PriceCoefController extends BaseController implements PriceCoefControllerI {

    @Autowired
    PriceCoefService priceCoefService;

    @Override
    public PriceCoef getPriceCoef(@PathVariable String id) {
        return priceCoefService.getById(parseID(id));
    }

    @Override
    public List<PriceCoef> getPriceCoefs() {
        return priceCoefService.getAll();
    }

    @Override
    public PriceCoef addPriceCoef(@RequestBody String json) {
        return priceCoefService.add(json);
    }

    @Override
    public PriceCoef editPriceCoef(@PathVariable String id, @RequestBody String json) {
        return priceCoefService.edit(parseID(id), json);
    }

    @Override
    public PriceCoef deletePriceCoef(@PathVariable String id) {
        return priceCoefService.delete(parseID(id));
    }
}
