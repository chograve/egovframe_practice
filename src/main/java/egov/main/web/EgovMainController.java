package egov.main.web;

import java.util.HashMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
// import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.lib.model.UserVO;
import egov.main.service.EgovMainService;

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

    try {
      resultMap = egovMainService.selectLogin(request);


      LOGGER.info(resultMap.get("uservo").toString());


      HttpSession session = request.getSession();
      session.setAttribute("uservo", resultMap.get("uservo"));

      model.addAttribute("id", ((UserVO) resultMap.get("uservo")).getUser_id());
      model.addAttribute("uservo", (UserVO) resultMap.get("uservo"));

    } catch (Exception e) {
      // TODO: handle exception
      LOGGER.info(e.getMessage());
      return "error/error";
    }
    // return "main/main";
    return "redirect:/boardWrite.do";
  }

  @RequestMapping(value = "/logout.do")
  public String logout(HttpSession session) {
    return "redirect:/login.do";
  }

}

