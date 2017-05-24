package com.gome.bi.monitor.common.util.redis;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class ProtoStuffStringRedisSerializer implements RedisSerializer<String> {
	static final byte[] EMPTY_ARRAY = new byte[0];
	@Override
	public String deserialize(byte[] paramArrayOfByte) throws SerializationException {
		if (paramArrayOfByte == null || paramArrayOfByte.length == 0) {
			return null;
		}
		return ProtoStuffSerializerUtil.deserialize(paramArrayOfByte, String.class);
	}

	@Override
	public byte[] serialize(String param) throws SerializationException {
		if (StringUtils.isEmpty(param)) {
			return EMPTY_ARRAY;
		}
		return ProtoStuffSerializerUtil.serialize(param);
	}
}
