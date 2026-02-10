package egov.board.service;

import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;


public interface EgovBoardService {

  public void checkUser(HttpServletRequest request) throws Exception;

  public void boardInsert(HttpServletRequest request) throws Exception;

  public HashMap<String, Object> boardView(HttpServletRequest request) throws Exception;

  public List<HashMap<String, Object>> showBaord(HttpServletRequest request) throws Exception;

  public String checkReply(HttpServletRequest request) throws Exception;



}
