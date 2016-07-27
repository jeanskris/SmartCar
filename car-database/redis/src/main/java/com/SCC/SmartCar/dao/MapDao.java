package com.SCC.SmartCar.dao;

import com.SCC.SmartCar.model.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

/**
 * Created by ZJDX on 2016/7/8.
 */
@Repository
public class MapDao {
    private static Logger logger = LoggerFactory.getLogger(MapDao.class);

    @Autowired
    private RedisTemplate<String,Map> redisTemplate;

    public void save(Map map) {
        /*redisTemplate.opsForList();
        redisTemplate.opsForSet();
        redisTemplate.opsForHash()*/
        ValueOperations<String, Map> valueOper = redisTemplate.opsForValue();
        valueOper.set(String.valueOf(map.getId()), map);
    }

    public Map read(int id) {
        ValueOperations<String, Map> valueOper = redisTemplate.opsForValue();
        Map map=valueOper.get(String.valueOf(id));
        logger.info("read:"+map.getId());
        return map;
    }

    public void delete(int id) {
        ValueOperations<String, Map> valueOper = redisTemplate.opsForValue();
        RedisOperations<String, Map> RedisOperations  = valueOper.getOperations();
        RedisOperations.delete(String.valueOf(id));
    }
}
