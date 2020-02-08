package com.womenhz.swee.web;

import java.nio.charset.StandardCharsets;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.hash.Hashing;
import com.womenhz.swee.model.User;
import com.womenhz.swee.service.UserService;
import lombok.Builder;
import lombok.Data;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private StringRedisTemplate redisTemplate;


    @GetMapping("/get")
    User getById(@RequestParam Long id){
        return userService.getById(id);
    }

    @PostMapping("/create")
    Long create(@RequestBody User user) {
        return userService.create(user);
    }

    @PostMapping("/set")
    void set(@RequestBody ParamDTO paramDTO) {
        userService.set(paramDTO.key, paramDTO.obj);
        System.out.println("key= "+userService.get(paramDTO.key));
    }

    @Data
    @Builder
    static class ParamDTO {

        private String key;

        private Object obj;
    }

    /**
     * 短地址服务
     *
     * */

    @GetMapping("/url")
    public String getUrl(@RequestParam String id) {
        return redisTemplate.opsForValue().get(id);
    }

    @PostMapping("/id")
    public String url2Id(@RequestParam String url) {
        UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});
        if (urlValidator.isValid(url)) {
            String id = String.valueOf(Hashing.murmur3_32().hashString(url, StandardCharsets.UTF_8));
            redisTemplate.opsForValue().set(id, url);
            return id;
        }
        return "";
    }


}
