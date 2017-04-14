/**
 * 
 */
package com.hanuritien.processor.detection.coordinates.impl;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanuritien.processor.detection.address.Address;
import com.hanuritien.processor.detection.address.AddressChecker;
import com.hanuritien.processor.detection.address.AddressService;
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
	
	@Resource(name = "addressService")
	AddressService addsvc;
	
	@Autowired
	AddressChecker addchk;

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
			if (tmp.getAddress() == null) {
				Address t = addsvc.findByCode(tmp.getCode());
				if (t != null) {
					tmp.setAddress(t.getAddress());
				}
			}
			
			coorddao.save(tmp);
			addchk.addCoordinatesVO(tmp);
		}
	}

	@Override
	public List<CoordinatesVO> findAll() throws Exception {
		return coorddao.findAll();
	}

}
