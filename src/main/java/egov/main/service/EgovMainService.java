package egov.main.service;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

public interface EgovMainService {

  public HashMap<String, Object> selectTblUser(HttpServletRequest request) throws Exception;

  public HashMap<String, Object> selectLogin(HttpServletRequest request) throws Exception;

}
