package com.oil.mapper;

import com.oil.repos.OilRepo1;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.Cacheable;

import java.util.HashMap;

@CacheNamespace(implementation = org.mybatis.caches.redis.RedisCache.class)
@Mapper
public interface OilInfoDAO {


    @Insert("insert into passages(`title`, `url`, `sourceUrl`, `area`, `date`) values (#{title}, #{url}, #{sourceUrl}, #{area}, #{date})")
    public int insertOilInfo(OilRepo1 oilRepo);

    @Select("select * from passages where id = #{id}")
    public HashMap searchOilInfo(@Param("id") int id);

}
