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
	
	@Resource(name = "addressService")
	AddressService addsvc;
	
	@PostConstruct
	public void init() throws Exception {
		tree = RTree.star().create();
		List<CoordinatesVO> coords = coordsvc.findAll();
		
		for (CoordinatesVO tmp : coords) {
			List<Rectangle> rec = GeomatrixUtils.getBounds(tmp);
			for (Rectangle t : rec) {
				CoordinatesVO newvo = new CoordinatesVO(tmp.getId(), tmp.getType(), tmp.getCode(), tmp.getName(), null, null);
				tree = tree.add(newvo, t);
			}
		}
		
		tree.visualize(2000, 2000).save("e:/mytree.png");
	}
	
	public List<CoordinatesVO> searchCoordinatesVO(float x, float y) {
		List<CoordinatesVO> ret = new ArrayList<>();

		logger.info(Float.toString(x));
		logger.info(Float.toString(y));
		
/*		tree.search(Geometries.point(x,y)).count();
		
		tree.search(Geometries.point(x,y)).filter(new Func1<Entry<CoordinatesVO, Geometry>, Boolean>() {

			@Override
			public Boolean call(Entry<CoordinatesVO, Geometry> t) {
				Boolean ret = false; 
				
				logger.info(t.value().getName());
				ret = true;
				
				return ret;
			}
		}).toBlocking().toString();*/
		
		List<Entry<CoordinatesVO, Geometry>> list = tree.search(Geometries.point(x,y)).toList().toBlocking().single();
		for (Entry<CoordinatesVO, Geometry> tmp : list) {
			Address add = addsvc.findByCode(tmp.value().getCode());
			if (add != null) {
				logger.info(add.getAddress());	
			}
			//logger.info(tmp.value().getCode() + " " + tmp.value().getName());
		}
		
		return ret;
	}
}
