package com.gome.bi.monitor.common.util.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis操作工具类，默认存储类型都为String
 * 
 * @author QYANZE
 *
 */
@Component
public class RedisCache {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	/**
	 * 实现命令：KEYS pattern，查找所有符合给定模式 pattern的 key
	 * 
	 * @param pattern
	 * @return
	 */
	public Set<String> keys(String pattern) {
		return redisTemplate.keys(pattern);
	}

	/**
	 * 实现命令：DEL key，删除一个key
	 * 
	 * @param key 键
	 */
	public void del(String key) {
		redisTemplate.delete(key);
	}

	/**
	 * 实现命令：TTL key，以timeUnit(天、时、分、秒)为单位，返回给定 key的剩余生存时间(TTL, time to live)。
	 * 
	 * @param key 键
	 * @param timeUnit 天、时、分、秒为单位
	 * @return
	 */
	public long ttl(String key, TimeUnit timeUnit) {
		return redisTemplate.getExpire(key, timeUnit);
	}

	/**
	 * 实现命令：SET key value，设置一个key-value（将字符串值 value关联到 key）
	 * 
	 * @param key 键
	 * @param value 值
	 */
	public void set(String key, String value) {
		BoundValueOperations<String, String> vop = redisTemplate.boundValueOps(key);
		vop.set(value);
	}

	/**
	 * 实现命令：SET key value EX seconds，设置key-value和超时时间
	 * 
	 * @param key 键
	 * @param value 值
	 * @param expire 生存时间值
	 * @param timeUnit 时间单位：天、时、分、秒
	 */
	public void set(String key, String value, long expire, TimeUnit timeUnit) {
		BoundValueOperations<String, String> vop = redisTemplate.boundValueOps(key);
		vop.set(value, expire, timeUnit);
	}

	/**
	 * 实现命令：GET key，返回 key所关联的字符串值。
	 * 
	 * @param key 键
	 * @return
	 */
	public String get(String key) {
		BoundValueOperations<String, String> vop = redisTemplate.boundValueOps(key);
		return vop.get();
	}

	/**
	 * 实现命令：HSET key field value，将哈希表 key中的域 field的值设为 value
	 * 
	 * @param key 键
	 * @param field hash表键
	 * @param value hash表值
	 */
	public void hset(String key, String field, String value) {
		BoundHashOperations<String, Object, Object> hop = redisTemplate.boundHashOps(key);
		hop.put(field, value);
	}

	/**
	 * 实现命令：HGET key field，返回哈希表 key中给定域 field的值
	 * 
	 * @param key 键
	 * @param field hash表键
	 * @return
	 */
	public String hget(String key, String field) {
		BoundHashOperations<String, Object, Object> hop = redisTemplate.boundHashOps(key);
		return (String) hop.get(field);
	}

	/**
	 * 实现命令：HDEL key field [field ...]，删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。
	 * 
	 * @param key 键
	 * @param fields hash表键
	 */
	public void hdel(String key, Object... fields) {
		BoundHashOperations<String, Object, Object> hop = redisTemplate.boundHashOps(key);
		hop.delete(fields);
	}

	/**
	 * 实现命令：HGETALL key，返回哈希表 key中，所有的域和值。
	 * 
	 * @param key 键
	 * @return
	 */
	public Map<Object, Object> hgetAll(String key) {
		BoundHashOperations<String, Object, Object> hop = redisTemplate.boundHashOps(key);
		return hop.entries();
	}

	/**
	 * 实现命令：LPUSH key value，将一个值 value插入到列表 key的表头
	 * 
	 * @param key 键
	 * @param value 值
	 * @return 执行 LPUSH命令后，列表的长度。
	 */
	public long lpush(String key, String value) {
		BoundListOperations<String, String> lop = redisTemplate.boundListOps(key);
		Long size = lop.leftPush(value);
		return size;
	}

	/**
	 * 实现命令：LPOP key，移除并返回列表 key的头元素。
	 * 
	 * @param key 键
	 * @return 列表key的头元素。
	 */
	public String lpop(String key) {
		BoundListOperations<String, String> lop = redisTemplate.boundListOps(key);
		String str = lop.leftPop();
		return str;
	}

	/**
	 * 实现命令：RPUSH key value，将一个值 value插入到列表 key的表尾(最右边)。
	 * 
	 * @param key 键
	 * @param value 值
	 * @return 执行 RPUSH命令后，列表的长度。
	 */
	public long rpush(String key, String value) {
		BoundListOperations<String, String> lop = redisTemplate.boundListOps(key);
		Long size = lop.rightPush(value);
		return size;
	}

	/**
	 * 实现命令：RPOP key，移除并返回列表 key的尾元素。
	 * 
	 * @param key 键
	 * @return 列表key的头元素。
	 */
	public String rpop(String key) {
		BoundListOperations<String, String> lop = redisTemplate.boundListOps(key);
		String str = lop.rightPop();
		return str;
	}

	/**
	 * 实现命令：LRANGE key start end，返回start-end之间的列表数据
	 * 
	 * @param key 键
	 * @param start 开始索引
	 * @param end 结束索引
	 * @return
	 */
	public List<String> lrange(String key, long start, long end) {
		BoundListOperations<String, String> lop = redisTemplate.boundListOps(key);
		return lop.range(start, end);
	}

	/**
	 * 实现命令：LRANGE key start end，返回所有的列表数据
	 * 
	 * @param key 键
	 * @return
	 */
	public List<String> lrangeAll(String key) {
		BoundListOperations<String, String> lop = redisTemplate.boundListOps(key);
		return lop.range(0, lop.size());
	}

	/**
	 * 实现命令：SADD key member，将一个 member元素加入到集合 key当中，已经存在于集合的 member元素将被忽略。
	 * 
	 * @param key 键
	 * @param member
	 */
	public void sadd(String key, String member) {
		BoundSetOperations<String, String> sop = redisTemplate.boundSetOps(key);
		sop.add(member);
	}

	/**
	 * 实现命令：SMEMBERS key，返回集合 key 中的所有成员。
	 * 
	 * @param key 键
	 * @return
	 */
	public Set<String> smembers(String key) {
		BoundSetOperations<String, String> sop = redisTemplate.boundSetOps(key);
		return sop.members();
	}

	/**
	 * 实现命令：ZADD key score member，将一个 member元素及其 score值加入到有序集 key当中。
	 * 
	 * @param key 键
	 * @param member 成员
	 * @param score 分数
	 */
	public void zadd(String key, String member, double score) {
		BoundZSetOperations<String, String> zop = redisTemplate.boundZSetOps(key);
		zop.add(member, score);
	}

	/**
	 * 实现命令：ZRANGE key start stop，返回有序集 key中，指定区间内的成员。
	 * 
	 * @param key 键
	 * @param start 开始分数
	 * @param end 结束分数
	 * @return
	 */
	public Set<String> zrange(String key, double start, double end) {
		BoundZSetOperations<String, String> zop = redisTemplate.boundZSetOps(key);
		return zop.rangeByScore(start, end);
	}
}
