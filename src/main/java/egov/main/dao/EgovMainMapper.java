package egov.main.dao;

import java.util.HashMap;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
import com.lib.model.UserVO;


@Mapper("mainMapper")
public interface EgovMainMapper {

  public HashMap<String, Object> selectTblUser(HashMap<String, Object> param) throws Exception;

  public UserVO selectLogin(HashMap<String, Object> param) throws Exception;

}
