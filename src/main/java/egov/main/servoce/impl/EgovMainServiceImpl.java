package egov.main.servoce.impl;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egov.main.dao.EgovMainMapper;
import egov.main.service.EgovMainService;
import egovframework.example.sample.service.impl.EgovSampleServiceImpl;

@Service("mainService")
public class EgovMainServiceImpl extends EgovAbstractServiceImpl implements EgovMainService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovSampleServiceImpl.class);

	@Resource(name = "mainMapper")
	EgovMainMapper egovMainMapper;

	@Override
	public HashMap<String, Object> selectTblUser(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, Object> paramMap= new HashMap<>();
		return egovMainMapper.selectTblUser(paramMap);
	}

	@Override
	public HashMap<String, Object> selectLogin(HttpServletRequest request) throws Exception {
				

		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		LOGGER.info("id=" + id);
		LOGGER.info("password=" + password);
		
		if(id.equals("admin")) {
			System.out.println("ture");
			throw new Exception("login error");		
		}
		
		HashMap<String, Object> paramMap= new HashMap<>();
		
		paramMap.put("id", id);
		paramMap.put("password", password);				

		// TODO Auto-generated method stub
		return egovMainMapper.selectLogin(paramMap);
	}

}
