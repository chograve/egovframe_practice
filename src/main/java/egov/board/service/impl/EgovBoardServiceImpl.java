package egov.board.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.lib.model.UserVO;
import com.lib.util.Validation_Form;
import egov.board.dao.EgovBoardMapper;
import egov.board.service.EgovBoardService;
import egovframework.example.sample.service.impl.EgovSampleServiceImpl;

@Service("boardService")
public class EgovBoardServiceImpl extends EgovAbstractServiceImpl implements EgovBoardService {

  private static final Logger LOGGER = LoggerFactory.getLogger(EgovSampleServiceImpl.class);

  @Resource(name = "boardMapper")
  EgovBoardMapper egovBoardMapper;

  @Override
  public void checkUser(HttpServletRequest request) throws Exception {
    // TODO Auto-generated method stub

    UserVO uservo = (UserVO) request.getSession().getAttribute("uservo");

    if (uservo == null) {
      throw new Exception("login error");
    }
  }



  @Override
  public void boardInsert(HttpServletRequest request) throws Exception {
    // TODO Auto-generated method stub

    UserVO uservo = (UserVO) request.getSession().getAttribute("uservo");

    String title = request.getParameter("title");

    String content = request.getParameter("content");

    if (title.length() > 25) {
      throw new Exception("check title");
    }

    HashMap<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("title", title);
    paramMap.put("content", content);
    paramMap.put("id", uservo.getUser_id());

    egovBoardMapper.boardInsert(paramMap);

  }



  @Override
  public HashMap<String, Object> boardView(HttpServletRequest request) throws Exception {
    // TODO Auto-generated method stub

    UserVO uservo = (UserVO) request.getSession().getAttribute("uservo");

    if (uservo.getUser_id() == null) {
      throw new Exception("no login");
    }

    String board_id = request.getParameter("board_id");

    boolean VaildNum = false;

    VaildNum = Validation_Form.validNum(board_id);

    if (!VaildNum) {
      throw new Exception("no board_id");
    }

    HashMap<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("board_id", board_id);
    paramMap.put("status", null);

    HashMap<String, Object> resultMap = new HashMap<String, Object>();
    resultMap = egovBoardMapper.boardView(paramMap);
    if (resultMap == null) {
      throw new Exception("no board");
    }

    return resultMap;
  }



  @Override
  public List<HashMap<String, Object>> showBaord(HttpServletRequest request) throws Exception {
    // TODO Auto-generated method stub
    HashMap<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("status", null);

    List<HashMap<String, Object>> resultList = new ArrayList<>();
    resultList = egovBoardMapper.showBaord(paramMap);

    if (resultList.size() == 0) {
      LOGGER.info("no list");
      throw new Exception("no list");
    }

    return resultList;
  }



}
