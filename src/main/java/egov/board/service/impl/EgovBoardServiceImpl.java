package egov.board.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.lib.model.UserVO;
import com.lib.util.Validation_Form;
import egov.board.dao.EgovBoardMapper;
import egov.board.service.EgovBoardService;


@Service("boardService")
public class EgovBoardServiceImpl extends EgovAbstractServiceImpl implements EgovBoardService {

  private static final Logger LOGGER = LoggerFactory.getLogger(EgovBoardServiceImpl.class);

  @Resource(name = "boardMapper")
  EgovBoardMapper egovBoardMapper;

  @Resource(name = "fileUploadProperty")
  Properties properties;

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
    paramMap.put("id", uservo.getUser_id());
    paramMap.put("status", 0);
    paramMap.put("out_boardid", 0);

    egovBoardMapper.boardInsert(paramMap);

    LOGGER.info("out_boardid============" + Integer.parseInt(paramMap.get("out_boardid").toString()));

    int boardid = Integer.parseInt(paramMap.get("out_boardid").toString());

    // 파일저장
    String uploadPath = properties.getProperty("file.imagePath");
    String convertuid = "";
    String filePath = "";

    LOGGER.info("uploadPath============" + uploadPath);

    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    String formatedNow = now.format(formatter);

    if (request instanceof MultipartHttpServletRequest) {

      LOGGER.info("MultipartHttpServletRequest============");

      final MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartHttpServletRequest.getFileMap();

      File saveFolder = new File(uploadPath);
      if (!saveFolder.exists() || saveFolder.isFile()) {
        saveFolder.mkdir();
      }

      for (MultipartFile file : fileMap.values()) {

        LOGGER.info("file============" + file.getName());

        if (!file.getOriginalFilename().equals("") || !file.getOriginalFilename().isEmpty()) {
          int maxSize = 1000 * 1024 * 1024; // 10MB
          long fileSize = file.getSize();

          LOGGER.info("maxSize=====" + maxSize);
          LOGGER.info("fileSize=====" + fileSize);

          if (fileSize > maxSize) {
            LOGGER.info("fileSize > maxSize==================");
            throw new Exception("error");
          }

          try {

            String fileName = formatedNow + "_" + file.getName();

            String originFileName = file.getOriginalFilename();
            int index = originFileName.lastIndexOf(".");
            String extension = originFileName.substring(index + 1);

            fileName = fileName + "." + extension;
            filePath = uploadPath + fileName;

            LOGGER.info("filetype============" + extension);
            LOGGER.info("filename============" + fileName);
            LOGGER.info("filetype============" + extension);
            LOGGER.info("filePath============" + filePath);

            file.transferTo(new File(uploadPath + fileName));



            HashMap<String, Object> paramMap2 = new HashMap<String, Object>();
            paramMap2.put("user_id", uservo.getUser_id());
            paramMap2.put("filename", fileName);
            paramMap2.put("filetype", extension);
            paramMap2.put("fileurl", "http://localhost:8080/Egov_WEB/boardView/image.do?file=" + fileName);
            paramMap2.put("status", 0);
            paramMap2.put("boardid", boardid);

            egovBoardMapper.saveFile(paramMap2);

          } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            LOGGER.info("e.getMessage()============" + e.getMessage());
          }

        }
      }
    }
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

    paramMap.put("pi_offset", (paginationInfo.getCurrentPageNo() - 1) * paginationInfo.getRecordCountPerPage());
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



  @Override
  public void saveReply(HttpServletRequest request) throws Exception {
    // TODO Auto-generated method stub

    UserVO uservo = (UserVO) request.getSession().getAttribute("uservo");

    if (uservo.getUser_id() == null) {
      throw new Exception("no login");
    }


    String title = request.getParameter("title");
    String content = request.getParameter("content");
    String origin_boardid = request.getParameter("origin_boardid");

    if (!Validation_Form.validNum(origin_boardid) || content.length() > 100000) {
      throw new Exception("error");
    }

    HashMap<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("title", title);
    paramMap.put("content", content);
    paramMap.put("origin_boardid", Integer.parseInt(origin_boardid));
    paramMap.put("user_id", uservo.getUser_id());
    paramMap.put("status", 0);

    egovBoardMapper.boardsaveReply(paramMap);

    LOGGER.info("status=" + paramMap.get("status").toString());

    if (Integer.parseInt(paramMap.get("status").toString()) != 1) {
      throw new Exception("error");
    }

  }



  @Override
  public HashMap<String, Object> loadFile(HttpServletRequest request) throws Exception {
    // TODO Auto-generated method stub
    String filename = request.getParameter("file").toString();

    LOGGER.info("filename============" + filename);

    String uploadPath = properties.getProperty("file.imagePath");

    LOGGER.info("uploadPath============" + uploadPath);

    InputStream in = null;
    byte[] byteArray = null;

    try {

      in = new FileInputStream(uploadPath + filename);
      byteArray = IOUtils.toByteArray(in);

    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    } finally {
      in.close();
    }

    HashMap<String, Object> resultMap = new HashMap<String, Object>();
    resultMap.put("bytedata", byteArray);
    resultMap.put("filename", filename);

    return resultMap;
  }

}

