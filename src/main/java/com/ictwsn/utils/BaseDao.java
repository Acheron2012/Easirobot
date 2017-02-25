package com.ictwsn.utils;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.io.Serializable;

@SuppressWarnings("restriction")
public class BaseDao {

	//JdbcTemplate
	@Resource
	protected JdbcTemplate jdbcTemplate;
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	//MyBats
	@Resource protected SqlSessionTemplate sqlSessionTemplate;
	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	//Redis
	@Resource protected RedisTemplate<Serializable, Serializable> redisTemplate;
	public RedisTemplate<Serializable, Serializable> getRedisTemplate() {
		return redisTemplate;
	}
	public void setRedisTemplate(
			RedisTemplate<Serializable, Serializable> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
}
