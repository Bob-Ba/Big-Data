package au.edu.utas.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Controller is the sign that mark this class as a controller managed by Spring
 */
@Controller
public class HelloController {

    /**
     * @RequestMapping("/hello") means that this method named "helloWorld" will receive a request from browser with "hello"
     * @ResponseBody means that this method can return values or parameters to the browser that can analyses and shows it rather than a page name (index.jsp)
     */
    @ResponseBody
    @RequestMapping(value = {"/hello"}, method = {RequestMethod.GET})
    public String hello(){
        return "Hello World!";
    }
}
