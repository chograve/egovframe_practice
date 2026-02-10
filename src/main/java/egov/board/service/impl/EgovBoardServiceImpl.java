package egov.board.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.lib.model.UserVO;
import com.lib.util.Validation_Form;
import egov.board.dao.EgovBoardMapper;
import egov.board.service.EgovBoardService;

@Service("boardService")
public class EgovBoardServiceImpl extends EgovAbstractServiceImpl implements EgovBoardService {

  private static final Logger LOGGER = LoggerFactory.getLogger(EgovBoardServiceImpl.class);

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

    String boardid = request.getParameter("board_id");

    boolean VaildNum = false;

    VaildNum = Validation_Form.validNum(boardid);

    if (!VaildNum) {
      throw new Exception("no board_id");
    }

    HashMap<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("boardid", boardid);
    paramMap.put("status", null);

    HashMap<String, Object> resultMap = new HashMap<String, Object>();
    resultMap = egovBoardMapper.boardView(paramMap);
    if (resultMap == null) {
      throw new Exception("no board");
    }
    resultMap.put("boardid", boardid);

    return resultMap;
  }



  @Override
  public List<HashMap<String, Object>> showBaord(HttpServletRequest request) throws Exception {
    // TODO Auto-generated method stub

    String pageNo = request.getParameter("pageNo");

    if (pageNo == null || pageNo.equals("")) {
      pageNo = "1";
    }

    PaginationInfo paginationInfo = new PaginationInfo();
    paginationInfo.setCurrentPageNo(Integer.parseInt(pageNo));
    paginationInfo.setPageSize(10);
    paginationInfo.setRecordCountPerPage(10);


    HashMap<String, Object> paramMap = new HashMap<String, Object>();

    paramMap.put("pi_offset",
        (paginationInfo.getCurrentPageNo() - 1) * paginationInfo.getRecordCountPerPage());
    paramMap.put("pi_recordCountPage", paginationInfo.getRecordCountPerPage());
    paramMap.put("out_listcount", 0);
    paramMap.put("status", 0);

    List<HashMap<String, Object>> resultList = new ArrayList<>();
    resultList = egovBoardMapper.showBaord(paramMap);

    int Totalcount = Integer.parseInt(paramMap.get("out_listcount").toString());
    paginationInfo.setTotalRecordCount(Totalcount);

    LOGGER.info("status=" + paramMap.get("status"));
    LOGGER.info("out_listcount=" + paramMap.get("out_listcount"));
    LOGGER.info("Totalcount=" + Totalcount);

    if (resultList.size() == 0) {
      LOGGER.info("no list");
      throw new Exception("no list");
    }

    HashMap<String, Object> resultMap = new HashMap<String, Object>();
    resultMap.put("paginationInfo", paginationInfo);
    resultMap.put("count", Totalcount);

    resultList.add(resultMap);

    return resultList;
  }



  @Override
  public String checkReply(HttpServletRequest request) throws Exception {
    // TODO Auto-generated method stub
    String boardid = request.getParameter("boardid").toString();
    LOGGER.info("boardid=" + boardid);
    LOGGER.info("Validation_Form.validNum(boardid)=" + Validation_Form.validNum(boardid));
    if (!Validation_Form.validNum(boardid)) {
      throw new Exception("don't equal boardid");
    }
    return boardid;
  }


}

