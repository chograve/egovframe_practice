package egov.board.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.lib.model.UserVO;
import egov.board.service.EgovBoardService;
import egov.main.web.EgovMainController;

@Controller
public class BoardController {

  private static final Logger LOGGER = LoggerFactory.getLogger(EgovMainController.class);

  @Resource(name = "boardService")
  EgovBoardService egovBoardService;

  @RequestMapping(value = "/boardWrite.do")
  public String boardWrite(HttpServletRequest request, ModelMap model) {
    LOGGER.info("boardWrite.do");
    HashMap<String, Object> resultMap = new HashMap<String, Object>();

    try {
      egovBoardService.checkUser(request);
    } catch (Exception e) {
      String message = e.getMessage();
      if (message.endsWith("login error")) {
        return "redirect:/login.do";
      } else {
        return "error/error";
      }
    }
    return "board/boardWrite";
  }

  @RequestMapping(value = "/boardInsert.do")
  public String boardInsert(HttpServletRequest request, ModelMap model) {
    LOGGER.info("boardInsert.do");
    HashMap<String, Object> resultMap = new HashMap<String, Object>();

    try {
      egovBoardService.boardInsert(request);
    } catch (Exception e) {
      String message = e.getMessage();
      if (message.endsWith("login error")) {
        return "redirect:/login.do";
      } else if (message.endsWith("check title")) {
        return "redirect:/boardWrite.do";
      } else {
        return "error/error";
      }
    }
    return "board/boardWrite";
  }

  @RequestMapping(value = "/boardView.do")
  public String boardView(HttpServletRequest request, ModelMap model) {
    LOGGER.info("boardInsert.do");
    HashMap<String, Object> resultMap = new HashMap<String, Object>();

    try {
      resultMap = egovBoardService.boardView(request);
    } catch (Exception e) {

      String message = e.getMessage();

      if (message.endsWith("login error")) {
        return "redirect:/login.do";
      } else if (message.endsWith("no board_id")) {
        return "redirect:/boardWrite.do";
      } else if (message.endsWith("check title")) {
        return "redirect:/boardWrite.do";
      } else {
        return "error/error";
      }
    }

    /*
     * model.addAttribute("title", resultMap.get("title").toString()); model.addAttribute("userid",
     * resultMap.get("userid").toString()); model.addAttribute("boardcontents",
     * resultMap.get("boardcontents").toString()); model.addAttribute("board_id",
     * resultMap.get("board_id").toString());
     */


    HttpSession session = request.getSession();

    UserVO uservo = (UserVO) session.getAttribute("uservo");

    String user_id = uservo.getUser_id();

    model.addAttribute("loginid", user_id);
    model.addAllAttributes(resultMap);
    return "board/boardView";
  }

  @RequestMapping(value = "/boardList.do")
  public String boardList(HttpServletRequest request, ModelMap model) {
    LOGGER.info("boardInsert.do");

    List<HashMap<String, Object>> resultList = new ArrayList<>();

    try {
      resultList = egovBoardService.showBaord(request);
    } catch (Exception e) {

      String message = e.getMessage();

      if (message.endsWith("login error")) {
        return "redirect:/login.do";
      } else if (message.endsWith("no board_id")) {
        return "redirect:/boardWrite.do";
      } else if (message.endsWith("check title")) {
        return "redirect:/boardWrite.do";
      } else {
        return "error/error";
      }
    }

    LOGGER.info("resultList=" + resultList.size());
    model.addAllAttributes(resultList.get(resultList.size() - 1));
    // resultList.remove(resultList.size() - 1);
    model.addAttribute("list", resultList);

    return "board/boardList";
  }

  @RequestMapping(value = "/boardReply.do")
  public String boardReply(HttpServletRequest request, ModelMap model) {
    LOGGER.info("boardWrite.do");
    String boardid = null;
    try {
      boardid = egovBoardService.checkReply(request);
    } catch (Exception e) {
      String message = e.getMessage();
      if (message.endsWith("login error")) {
        return "redirect:/login.do";
      } else if (message.endsWith("don't equal boardid")) {
        return "redirect:/boardList.do";
      } else {
        return "error/error";
      }
    }

    model.addAttribute("boardid", boardid);
    return "board/boardReply";
  }

}
