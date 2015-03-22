package com.main.cloudapi.service;

import com.main.cloudapi.dao.BrandDAO;
import com.main.cloudapi.dao.CarDAO;
import com.main.cloudapi.entity.Brand;
import com.main.cloudapi.entity.Car;
import com.main.cloudapi.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * Created by mirxak on 24.01.15.
 */
@Service
public class BrandService {

    @Autowired
    BrandDAO brandDAO;

    @Autowired
    CarDAO carDAO;

    public List<Brand> getAllBrands(){
        List<Brand> brands = brandDAO.getAll(brandDAO.STD_OFFSET, brandDAO.STD_LIMIT);
        if ((brands == null) || (brands.isEmpty())){
            return Collections.emptyList();
        }
        return brands;
    }

    public Brand getById(Long id){
        return brandDAO.getById(id, true);
    }

    @Transactional
    public Brand addBrand(String json){
        Brand brand = JsonUtils.getFromJson(json, Brand.class, true);
        brandDAO.add(brand);
        return brand;
    }

    @Transactional
    public Brand editBrand(Long id, String json){
        Brand brand = getById(id);
        Brand edtBrand = JsonUtils.getFromJson(json, Brand.class, true);
        if (!StringUtils.isBlank(edtBrand.getName())){
            brand.setName(edtBrand.getName());
        }
        if (edtBrand.getServiceCoef() != null){
            brand.setServiceCoef(edtBrand.getServiceCoef());
        }

        return brandDAO.update(brand);
    }

    @Transactional
    public Brand deleteBrand(Long id){
        Brand brand = getById(id);

        if (!brand.getCars().isEmpty()){
            for (Car car : brand.getCars()){
                carDAO.delete(car);
                //удаление других
            }
        }

        brandDAO.delete(brand);
        return brand;
    }

    @Transactional
    public Brand simpleUpdate(Brand brand){
        return brandDAO.update(brand);
    }
}
