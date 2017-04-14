package com.hanuritien.processor.detection.address;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.PostLoad;

import org.springframework.stereotype.Component;

import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Geometry;
import com.github.davidmoten.rtree.geometry.Rectangle;
import com.hanuritien.processor.detection.coordinates.CoordinatesService;
import com.hanuritien.processor.detection.coordinates.CoordinatesVO;
import com.hanuritien.processor.detection.coordinates.GeomatrixUtils;

@Component("addressChecker")
public class AddressChecker {
	
	RTree<String, Geometry> tree;
	
	@Resource(name = "coordinatesService")
	CoordinatesService coordsvc;
	
	@PostConstruct
	public void init() throws Exception {
		tree = RTree.star().create();
		List<CoordinatesVO> coords = coordsvc.findAll();
		
		for (CoordinatesVO tmp : coords) {
			Rectangle rec = GeomatrixUtils.getBound(tmp);
			tree.add(tmp.getCode(), rec);
		}
		tree.add("aa", Geometries.rectangle(0, 0, 100, 100));
		
		tree.visualize(1000, 1000).save("d:/mytree.png");
	}

}
