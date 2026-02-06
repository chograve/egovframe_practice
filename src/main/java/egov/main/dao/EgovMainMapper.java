package egov.main.dao;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;


@Mapper("mainMapper")
public interface EgovMainMapper {
	
	public HashMap<String, Object> selectTblUser(HashMap<String, Object> param) throws Exception;
	
	public HashMap<String, Object> selectLogin(HashMap<String, Object> param) throws Exception;

}
