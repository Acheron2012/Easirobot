package com.ictwsn;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeAction {
	
	//欢迎界面（测试）
	@RequestMapping("/welcome.do")
    public String welcome(){
		return "index";
	}
	
}
