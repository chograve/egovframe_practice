package egov.main.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import egovframework.example.sample.service.impl.EgovSampleServiceImpl;

public interface EgovMainService{
	
	public HashMap<String, Object> selectTblUser(HttpServletRequest request) throws Exception;
	
	public HashMap<String, Object> selectLogin(HttpServletRequest request) throws Exception;

}
