package freeswitch.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author T@nvir
 */
@RestController
public class FsController {
    @RequestMapping("/hello")
    public String getName(){
        return "Hello World";
    }
}
