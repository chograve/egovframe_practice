package egov.main.web;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import egov.main.service.EgovMainService;
import egovframework.example.sample.service.impl.EgovSampleServiceImpl;

import org.slf4j.Logger;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class EgovMainController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovMainController.class);
	
	
	@Resource(name = "mainService")
	EgovMainService egovMainService;
	
	@RequestMapping(value = "/main.do")
	public String main(HttpServletRequest request, ModelMap model) {		
		return "/main/main";
	}
	
	@RequestMapping(value = "/main2.do")
	public String main2(HttpServletRequest request, ModelMap model) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = egovMainService.selectTblUser(request);	
				
		model.addAttribute("id", resultMap.get("user_id").toString());
		model.addAttribute("pw", resultMap.get("user_pw").toString());
		return "main/main";
	}
	
	@RequestMapping(value = "/login.do")
	public String login(HttpServletRequest request, ModelMap model) throws Exception {
		LOGGER.info("login.do");
		return "login/login";
	}
	
	@RequestMapping(value = "/loginSubmisson.do")
	public String loginSubmisson(HttpServletRequest request, ModelMap model) throws Exception {
	
		LOGGER.info("loginSubmisson.do");
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
				
		resultMap = egovMainService.selectLogin(request);
		
		if (resultMap != null && !resultMap.isEmpty()) {
			LOGGER.info("map은 비어있지 않습니다.");
			
			HttpSession session = request.getSession();
			session.setAttribute("id", resultMap.get("user_id".toString()));			
			model.addAttribute("id", resultMap.get("user_id".toString()));			
			return "main/main";			
		}else {
			LOGGER.info("map은 비어있습니다.");
			return "error/error";
		}
	}
	
	@RequestMapping(value = "/logout.do")
	public String logout(HttpSession session)  {					
		return "redirect:/login.do";		
	}

}

