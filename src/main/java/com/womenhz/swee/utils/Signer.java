package com.womenhz.swee.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Signer {

    public static void currentUserInfo(){
        String name = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            log.info((UserDetails) principal);
            name = ((UserDetails) principal).getUsername();
        }else {
            name = principal.toString();
        }
        log.info("name = "+name);
    }

}
