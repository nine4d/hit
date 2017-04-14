/**
 * 
 */
package com.hanuritien.processor.detection.coordinates.impl;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanuritien.processor.detection.coordinates.CoordinatesService;
import com.hanuritien.processor.detection.coordinates.CoordinatesVO;
import com.hanuritien.web.utils.DataTables.DataTablesInput;
import com.hanuritien.web.utils.DataTables.DataTablesOutput;

/**
 * @author changu
 *
 */
@Service("coordinatesService")
public class CoordinatesServiceImpl implements CoordinatesService {
	
	@Resource(name="CoordinatesDAO")
	CoordinatesDAO coorddao;
	

	/* (non-Javadoc)
	 * @see com.hanuritien.processor.detection.coordinates.CoordinatesService#getDatatables(com.hanuritien.web.utils.DataTables.DataTablesInput)
	 */
	@Override
	public DataTablesOutput<CoordinatesVO> getDatatables(DataTablesInput input) throws Exception {
		DataTablesOutput<CoordinatesVO> ret = new DataTablesOutput<>();
		ret.setDraw(input.getDraw());
		
		List<CoordinatesVO> tmp = coorddao.findAll();
		ret.setData(tmp);
		ret.setRecordsFiltered(tmp.size());
		ret.setRecordsTotal(tmp.size());
		return ret;
	}

	/* (non-Javadoc)
	 * @see com.hanuritien.processor.detection.coordinates.CoordinatesService#delete(java.util.List)
	 */
	@Override
	public void delete(List<CoordinatesVO> inputs) throws Exception {
		for (CoordinatesVO tmp : inputs) {
			coorddao.delete(tmp.getId());
		}
	}

	@Override
	public void save(List<CoordinatesVO> inputs) throws Exception {
		for (CoordinatesVO tmp : inputs) {
			if (tmp.getId() == null || tmp.getId().isEmpty()) {
				tmp.setId(UUID.randomUUID().toString().replaceAll("-", ""));
			}
			coorddao.save(tmp);
		}
	}

	@Override
	public List<CoordinatesVO> findAll() throws Exception {
		return coorddao.findAll();
	}

}
