/**
 * 
 */
package com.hanuritien.processor.detection.coordinates.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.hanuritien.processor.detection.coordinates.CoordinatesVO;

/**
 * @author changu
 *
 */
@Repository("CoordinatesDAO")
public class CoordinatesDAO {
	
	@Autowired
	RedisTemplate redisTemplate;
	
	private final String COORDINATE_KEY = "Coordinate";
	
	void save(CoordinatesVO input) {
		this.redisTemplate.opsForHash().put(COORDINATE_KEY, input.getId(), input);
	}
	
	CoordinatesVO find(String id) {
		return (CoordinatesVO)this.redisTemplate.opsForHash().get(COORDINATE_KEY, id);
	}
	
	List<CoordinatesVO> findAll() {
		return new ArrayList(this.redisTemplate.opsForHash().entries(COORDINATE_KEY).values());
	}
	
	void delete(String id) {
		this.redisTemplate.opsForHash().delete(COORDINATE_KEY, id);
	}
}
