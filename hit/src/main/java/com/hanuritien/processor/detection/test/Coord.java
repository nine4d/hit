package com.hanuritien.processor.detection.test;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("coord")
public class Coord {
  @Id 
  String id;
  @Indexed 
  String name;
  String type;
  String coord;
}
