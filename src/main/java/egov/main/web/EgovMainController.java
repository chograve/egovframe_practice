package egov.main.web;

import java.io.IOException;
import java.util.HashMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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


      LOGGER.info(("ST 접속정보기록==============" + ((UserVO) resultMap.get("uservo")).getUser_id()));
      LOGGER.info(("user_id==============" + ((UserVO) resultMap.get("uservo")).getUser_id()));
      LOGGER.info(("ED 접속정보기록==============" + ((UserVO) resultMap.get("uservo")).getUser_id()));

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
    return "redirect:/boardList.do";
  }

  @RequestMapping(value = "/logout.do")
  public String logout(HttpServletRequest request, ModelMap model) {
    request.getSession().invalidate();
    return "redirect:/login.do";
  }


  @RequestMapping(value = "/testjson.do")
  public void testJson(HttpServletRequest request, HttpServletResponse response) {

    LOGGER.info("testjson.do");

    try {
      request.setCharacterEncoding("UTF-8");

      JSONObject jsonObject = new JSONObject();
      jsonObject.put("name", "abc123");
      jsonObject.put("age", 100);
      jsonObject.put("text", "kakao");

      JSONObject jsonObject2 = new JSONObject();
      HashMap<String, Object> resultMap = new HashMap<String, Object>();
      resultMap.put("column1", 100);
      resultMap.put("column2", 101);
      resultMap.put("column3", 102);

      jsonObject2.putAll(resultMap);

      JSONObject jsonObject3 = new JSONObject();
      jsonObject3.put("name", "11");
      jsonObject3.put("age", 200);
      jsonObject3.put("text", "tatata");


      JSONArray jsonarray = new JSONArray();
      jsonarray.add(jsonObject2);
      jsonarray.add(jsonObject3);


      jsonObject.put("mylist", jsonarray);

      response.setCharacterEncoding("UTF-8");
      response.setContentType("application/json");

      response.getWriter().print(jsonObject);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @RequestMapping(value = "/test.do")
  public String test(HttpServletRequest request, HttpServletResponse response) {
    return "/common/test";
  }

}

