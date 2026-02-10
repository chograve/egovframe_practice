package egov.main.servoce.impl;

import java.util.HashMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.lib.model.UserVO;
import egov.main.dao.EgovMainMapper;
import egov.main.service.EgovMainService;

@Service("mainService")
public class EgovMainServiceImpl extends EgovAbstractServiceImpl implements EgovMainService {

  private static final Logger LOGGER = LoggerFactory.getLogger(EgovMainServiceImpl.class);

  @Resource(name = "mainMapper")
  EgovMainMapper egovMainMapper;

  @Override
  public HashMap<String, Object> selectTblUser(HttpServletRequest request) throws Exception {
    // TODO Auto-generated method stub
    HashMap<String, Object> paramMap = new HashMap<>();
    return egovMainMapper.selectTblUser(paramMap);
  }

  @Override
  public HashMap<String, Object> selectLogin(HttpServletRequest request) throws Exception {


    String id = request.getParameter("id");
    String password = request.getParameter("password");

    LOGGER.info("id=" + id);
    LOGGER.info("password=" + password);

    if (id.equals("admin")) {
      System.out.println("ture");
      throw new Exception("login error");
    }

    HashMap<String, Object> paramMap = new HashMap<>();

    paramMap.put("id", id);
    paramMap.put("password", password);

    // TODO Auto-generated method stub

    UserVO userVo = egovMainMapper.selectLogin(paramMap);

    HashMap<String, Object> resultMap = new HashMap<>();
    resultMap.put("uservo", userVo);

    return resultMap;
  }

}
