package com.SCC.SmartCar.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ZJDX on 2016/6/20.
 */
@Controller
public class webAPI {
    private static Logger logger = LoggerFactory.getLogger(webAPI.class);


    /**
     * index1
     * when client request"/",server return index.html
     * @return no
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index1(){
        System.out.println("/");
        return new ModelAndView("hello");
    }
    /**
     * index
     * when client request"/index.html",server return index.html
     * @return no
     */
    @CrossOrigin
    @RequestMapping(value = "/index.html")
    public ModelAndView index(){
        System.out.println("/index");
        ModelAndView mv = new ModelAndView("index");

        return mv;
    }


}
