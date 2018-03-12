package com.ictwsn;

import com.hankcs.hanlp.seg.Viterbi.ViterbiSegment;
import com.hankcs.hanlp.seg.common.Term;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.*;
import java.util.List;

@Controller
public class WelcomeAction {
	
	//欢迎界面（测试）
	@RequestMapping("/welcome.do")
    public String welcome() {
			System.out.println("测试页面");
			return "index";
	}

	//欢迎界面（测试）
	@RequestMapping("/hanlp.do")
	public void hanlp(){
		String[] testCase = new String[]{
				"签约仪式前，秦光荣、李纪恒、仇和等一同会见了参加签约的企业家。",
				"龚学平等领导,邓颖超生前",
				"中关村今天天气怎么样",
				"只瓜是笨蛋"
		};
		for (String sentence : testCase) {
			List<Term> termList = new ViterbiSegment().enableCustomDictionary(true).seg(sentence);
			System.out.println(termList);
		}
	}
	
}
