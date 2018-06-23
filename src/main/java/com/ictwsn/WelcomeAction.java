package com.ictwsn;

import com.ictwsn.rob.schedule.UpdateCronTask;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WelcomeAction {
	
	//欢迎界面（测试）
	@RequestMapping("/welcome.do")
    public String welcome() {
			System.out.println("测试页面");
			return "index";
	}

//	json格式接收
//	@RequestMapping(value = "/addObj",method= RequestMethod.POST)
//	@ResponseBody
//	public String addObj(@RequestBody JSONObject jsonObject) {
//		System.out.println(jsonObject.toString());
//		return null;
//	}

	//欢迎界面（测试）
	@RequestMapping("/test.do")
	public void TestDo(){
		UpdateCronTask.cron = "0/10 * * * * ?";
//		String[] testCase = new String[]{
//				"签约仪式前，秦光荣、李纪恒、仇和等一同会见了参加签约的企业家。",
//				"龚学平等领导,邓颖超生前",
//				"中关村今天天气怎么样",
//				"只瓜是笨蛋"
//		};
//		for (String sentence : testCase) {
//			List<Term> termList = new ViterbiSegment().enableCustomDictionary(true).seg(sentence);
//			System.out.println(termList);
//		}
	}
	
}
