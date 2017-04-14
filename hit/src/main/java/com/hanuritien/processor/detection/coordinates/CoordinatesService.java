/**
 * 
 */
package com.hanuritien.processor.detection.coordinates;

import java.util.List;

import com.hanuritien.web.utils.DataTables.DataTablesInput;
import com.hanuritien.web.utils.DataTables.DataTablesOutput;

/**
 * @author changu
 *
 */
public interface CoordinatesService {
	DataTablesOutput<CoordinatesVO> getDatatables(DataTablesInput input) 
			throws Exception;
	
	void save(List<CoordinatesVO> inputs) throws Exception;
	
	void delete(List<CoordinatesVO> inputs) throws Exception;
	
	List<CoordinatesVO> findAll() throws Exception;
}
