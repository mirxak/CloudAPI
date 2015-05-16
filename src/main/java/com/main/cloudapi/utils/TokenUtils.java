package com.main.cloudapi.utils;

import com.main.cloudapi.constmes.ThrowFabric;
import com.main.cloudapi.entity.User;
import com.main.cloudapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by mirxak on 16.05.15.
 */
@Service
public class TokenUtils {

    private static final int SALT_LENGTH = 20;

    @Autowired
    private UserService userService;

    private static final String magicWord="#k-lab_magic/Word!";
    private static final Random r = new SecureRandom();

    public String getToken(User user) {
        byte[] salt = new byte[SALT_LENGTH];
        r.nextBytes(salt);
        String s=new String(salt)+user.getId()+magicWord;
        String token="";
        try {

            byte[] bytesOfMessage = s.getBytes("UTF-8");
            token= DigestUtils.md5DigestAsHex(bytesOfMessage);
            Long id=user.getId();

            if(id==null){
                return null;
            }

        } catch (Exception e) {
            throw new ThrowFabric.LockedException("fail in auth");
        }
        return token;
    }

}
