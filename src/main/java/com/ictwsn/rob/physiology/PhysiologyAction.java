package com.ictwsn.rob.physiology;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rob/phy")
public class PhysiologyAction {

    public static Logger logger = LoggerFactory.getLogger(PhysiologyAction.class);

}
