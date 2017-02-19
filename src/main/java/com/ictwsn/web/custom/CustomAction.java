package com.ictwsn.web.custom;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web/cust")
public class CustomAction {

    public static Logger logger = LoggerFactory.getLogger(CustomAction.class);

}
