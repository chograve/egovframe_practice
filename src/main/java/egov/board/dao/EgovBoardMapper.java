package egov.board.dao;

import java.util.HashMap;
import java.util.List;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;


@Mapper("boardMapper")
public interface EgovBoardMapper {
  public void boardInsert(HashMap<String, Object> paramMap) throws Exception;

  public HashMap<String, Object> boardView(HashMap<String, Object> paramMap) throws Exception;

  public List<HashMap<String, Object>> showBaord(HashMap<String, Object> paramMap) throws Exception;


}
