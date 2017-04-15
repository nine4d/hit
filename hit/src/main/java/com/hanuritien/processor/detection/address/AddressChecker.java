package com.hanuritien.processor.detection.address;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esri.core.geometry.OperatorContains;
import com.esri.core.geometry.OperatorTouches;
import com.esri.core.geometry.Polygon;
import com.esri.core.geometry.SpatialReference;
import com.github.davidmoten.rtree.Entry;
import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Geometry;
import com.github.davidmoten.rtree.geometry.Point;
import com.github.davidmoten.rtree.geometry.Rectangle;
import com.hanuritien.processor.detection.controller.CoordinateType;
import com.hanuritien.processor.detection.coordinates.CoordinatesService;
import com.hanuritien.processor.detection.coordinates.CoordinatesVO;
import com.hanuritien.processor.detection.coordinates.GeomatrixUtils;
import com.hanuritien.processor.detection.coordinates.LocationVO;

import rx.functions.Func1;

@Component("addressChecker")
public class AddressChecker {
	Logger logger = LoggerFactory.getLogger(AddressChecker.class);
	
	RTree<CoordinatesVO, Geometry> tree;
	
	@Resource(name = "coordinatesService")
	CoordinatesService coordsvc;
	
	SpatialReference sr = SpatialReference.create(4326);
	
	@PostConstruct
	public void init() throws Exception {
		tree = RTree.star().create();
		List<CoordinatesVO> coords = coordsvc.findAll();
		
		for (CoordinatesVO tmp : coords) {
			List<Rectangle> rec = GeomatrixUtils.getBounds(tmp);
			for (Rectangle t : rec) {
				tree = tree.add(tmp, t);
			}
		}
	}
	
	public void addCoordinatesVO(CoordinatesVO tmp) {
		List<Rectangle> rec = GeomatrixUtils.getBounds(tmp);
		for (Rectangle t : rec) {
			tree = tree.add(tmp, t);
		}
	}
	
	public List<CoordinatesVO> searchCoordinatesVO(float x, float y) {
		List<CoordinatesVO> ret = new ArrayList<>();
		final Point hitpoint = Geometries.point(x,y);
		logger.debug("x : " + Float.toString(x));
		logger.debug("y : " + Float.toString(y));
		// 필터를 삽입해서 셰이프 hittest 해야 함.
		List<Entry<CoordinatesVO, Geometry>> list = tree.search(hitpoint)
				.filter(new Func1<Entry<CoordinatesVO, Geometry>, Boolean>() {

					@Override
					public Boolean call(Entry<CoordinatesVO, Geometry> t) {
						Boolean ret = false;
						com.esri.core.geometry.Point chk = new com.esri.core.geometry.Point(hitpoint.x(), hitpoint.y());
						
						if (t.value().getType() == CoordinateType.Polygon) {
							Polygon p = GeomatrixUtils.getPolygon(t.value().getLocations());
							ret = OperatorContains.local().execute(p, chk, sr, null);
						} else if (t.value().getType() == CoordinateType.MultiPolygon) {
							for (List<LocationVO> arr : t.value().getMultiLocations()) {
								Polygon p = GeomatrixUtils.getPolygon(arr);
								ret = OperatorContains.local().execute(p, chk, sr, null);
								if (ret) 
									break;
							}
						}
						
						return ret;
					}
				})
				.toList().toBlocking().single();
		for (Entry<CoordinatesVO, Geometry> tmp : list) {
			logger.info(tmp.value().getAddress());
			ret.add(tmp.value());
		}
		
		return ret;
	}
}
