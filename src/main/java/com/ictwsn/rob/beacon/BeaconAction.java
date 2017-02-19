package com.ictwsn.rob.beacon;


import com.ictwsn.test.action.UserAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rob/beacon")
public class BeaconAction {

    public static Logger logger = LoggerFactory.getLogger(BeaconAction.class);

}
