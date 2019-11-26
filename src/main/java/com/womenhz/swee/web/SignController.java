package com.womenhz.swee.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.womenhz.swee.utils.Signer;

@RestController
@RequestMapping("/sign")
public class SignController {

    @GetMapping("/in")
    String signIn(){
        return "hello, security.";
    }

    @GetMapping("/current")
    void current(){
        Signer.currentUserInfo();
    }
}
