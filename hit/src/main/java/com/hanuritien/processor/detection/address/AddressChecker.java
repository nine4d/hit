package com.hanuritien.processor.detection.address;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.github.davidmoten.rtree.Entry;
import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Geometry;
import com.github.davidmoten.rtree.geometry.Point;
import com.github.davidmoten.rtree.geometry.Rectangle;
import com.hanuritien.processor.detection.coordinates.CoordinatesService;
import com.hanuritien.processor.detection.coordinates.CoordinatesVO;
import com.hanuritien.processor.detection.coordinates.GeomatrixUtils;

import rx.Observable;
import rx.functions.Func1;

@Component("addressChecker")
public class AddressChecker {
	Logger logger = LoggerFactory.getLogger(AddressChecker.class);
	
	RTree<CoordinatesVO, Geometry> tree;
	
	@Resource(name = "coordinatesService")
	CoordinatesService coordsvc;
	
	@PostConstruct
	public void init() throws Exception {
		tree = RTree.star().create();
		List<CoordinatesVO> coords = coordsvc.findAll();
		
		for (CoordinatesVO tmp : coords) {
			List<Rectangle> rec = GeomatrixUtils.getBounds(tmp);
			for (Rectangle t : rec) {
/*				CoordinatesVO newvo = new CoordinatesVO(
						tmp.getId(), 
						tmp.getType(), 
						tmp.getCode(), 
						tmp.getName(), 
						tmp.getAddress(),
						null, null);*/
				tree = tree.add(tmp, t);
			}
		}
	}
	
	public void addCoordinatesVO(CoordinatesVO tmp) {
		List<Rectangle> rec = GeomatrixUtils.getBounds(tmp);
		for (Rectangle t : rec) {
			CoordinatesVO newvo = new CoordinatesVO(
					tmp.getId(), 
					tmp.getType(), 
					tmp.getCode(), 
					tmp.getName(), 
					tmp.getAddress(),
					null, null);
			tree = tree.add(newvo, t);
		}
	}
	
	public List<CoordinatesVO> searchCoordinatesVO(float x, float y) {
		List<CoordinatesVO> ret = new ArrayList<>();

		logger.info(Float.toString(x));
		logger.info(Float.toString(y));
		
		List<Entry<CoordinatesVO, Geometry>> list = tree.search(Geometries.point(x,y)).toList().toBlocking().single();
		for (Entry<CoordinatesVO, Geometry> tmp : list) {
			logger.info(tmp.value().getAddress());	
			//logger.info(tmp.value().getCode() + " " + tmp.value().getName());
			//logger.info(tmp.value().getLocations().toString());	
		}
		
		return ret;
	}
}
