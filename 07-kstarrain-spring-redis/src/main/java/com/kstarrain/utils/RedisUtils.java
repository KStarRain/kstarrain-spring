package com.kstarrain.utils;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class RedisUtils{

    private static StringRedisTemplate stringRedisTemplate;


//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        RedisUtils.stringRedisTemplate = (StringRedisTemplate) applicationContext.getBean("stringRedisTemplate");
//    }

    public static void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        RedisUtils.stringRedisTemplate = stringRedisTemplate;
    }

    public static Boolean hasKey(String key) {
        Boolean b = stringRedisTemplate.hasKey(key);
        return b != null && b;
    }

    /**
     * 根据key从redis获取对应的value
     * @param key Redis key
     * @return Redis value
     */
    public static String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 设置普通value类型数据，永不过期
     * @param key Redis key
     * @param value Redis value
     */
    public static void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置普通value类型数据，并赋予过期时间
     * @param key Redis key
     * @param value Redis value
     * @param mExpire 过期时间（单位毫秒）
     */
    public static void set(String key, String value, long mExpire) {
        stringRedisTemplate.opsForValue().set(key, value, mExpire, TimeUnit.MILLISECONDS);
    }

    /**
     * 获取 hash 中 key 所对应的值
     * @param key hash的key
     * @param hashKey hash中的key
     * @return hash中的key对应的value
     */
    public static Object hGet(String key, Object hashKey) {
        return stringRedisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 给 hash 中的 key 设值
     * @param key hash的key
     * @param hashKey hash中的key
     * @param value hash中的key对应的value
     */
    public static void hPut(String key, Object hashKey, Object value) {
        stringRedisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 获取一整个 hash
     * @param key redis 的 key
     * @return hash 的 map
     */
    public static Map<Object, Object> hGetAll(String key) {
        return stringRedisTemplate.opsForHash().entries(key);
    }

    /**
     * 将 map 放入到 key 所对应的 hash 中的
     * @param key redis 的 key
     * @param map 想要放入 hash 的 map
     */
    public static void hPutAll(String key, Map<?, ?> map) {
        stringRedisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 设置过期时间
     * @param key Redis 的 key
     * @param expire 过期时间（单位：秒）
     * @return 是否设置成功
     */
    public static boolean expire(String key, long expire) {
        return stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    /**
     * 设置过期时间
     * @param key Redis 的 key
     * @param mExpire 过期时间（单位：毫秒）
     * @return 是否设置成功
     */
    public static boolean mExpire(String key, long mExpire) {
        return stringRedisTemplate.expire(key, mExpire, TimeUnit.MILLISECONDS);
    }

    public static void mExpire(final Collection<ExpireKey> keys) {
    	expire(keys,TimeUnit.MILLISECONDS);
    }

    public static void expire(final Collection<ExpireKey> keys) {
    	expire(keys,TimeUnit.SECONDS);
    }

    public static boolean sIsMember(String key, Object val) {
        Boolean b = stringRedisTemplate.opsForSet().isMember(key, val);
        return b != null && b;
    }

    public static Set<String> sMembers(String key) {
        return stringRedisTemplate.opsForSet().members(key);
    }
    
    /**
     * 基于内存的增量同步SET
     * @param key Set的key
     * @param newValues Set 中的新值集合
     */
    public static void syncSet(final String key, final Collection<String> newValues) {
        RedisUtils.syncSet(key, newValues, -1);
    }

    /**
     * 基于内存的增量同步SET
     * @param key Set 的 key
     * @param newValues Set 中的新值集合
     * @param mExpire 过期时间，-1 表示永不过期(单位：毫秒)
     */
    public static void syncSet(final String key, final Collection<String> newValues, final long mExpire) {
        SetOperations<String, String> opsForSet = stringRedisTemplate.opsForSet();
        RedisSerializer<String> serializer = stringRedisTemplate.getStringSerializer();
        Set<String> oldValues = opsForSet.members(key);

        // Don't have old data
        if (!isNotEmpty(oldValues)) {
            String[] tmp = new String[newValues.size()];
            newValues.toArray(tmp);
            opsForSet.add(key, tmp);
            return;
        }

        // add
        List<String> addValues = new ArrayList<>();
        addValues.addAll(newValues);
        addValues.removeAll(oldValues);
        final byte[][] addArr = serializeCollection(serializer, addValues);

        // minus
        List<String> minusValues = new ArrayList<>();
        minusValues.addAll(oldValues);
        minusValues.removeAll(newValues);
        final byte[][] minusArr = serializeCollection(serializer, minusValues);

        final byte[] keyBytes = serializer.serialize(key);

        // Have old data
        stringRedisTemplate.executePipelined(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                // do add
                if (isNotEmpty(addArr)) {
                    connection.sAdd(keyBytes, addArr);
                }
                // do minus
                if (isNotEmpty(minusArr)) {
                    connection.sRem(keyBytes, minusArr);
                }

                if (mExpire != -1) {
                    connection.pExpire(keyBytes, mExpire);
                }

                return null;
            }
        });

    }

    /**
     * 基于内存的增量同步HASH
     * @param key Hash 的 key
     * @param newMap Hash 的新值
     */
    public static void syncHash(final String key, final Map<?, ?> newMap) {
        RedisUtils.syncHash(key, newMap, -1);
    }

    /**
     * 基于内存的增量同步HASH
     * @param key Hash 的 key
     * @param newMap Hash 的新值
     * @param mExpire 过期时间，-1 表示永不过期(单位：毫秒)
     */
    public static void syncHash(final String key, final Map<?, ?> newMap, final long mExpire) {
        HashOperations<String, Object, Object> opsForHash = stringRedisTemplate.opsForHash();
        final RedisSerializer<String> serializer = stringRedisTemplate.getStringSerializer();
        final Map<Object, Object> oldMap = opsForHash.entries(key);

        // Don't have old data
        if (oldMap == null || oldMap.isEmpty()) {
            opsForHash.putAll(key, newMap);
            return;
        }

        Set<Object> oldKeys = oldMap.keySet();
        Set<?> newKeys = newMap.keySet();

        // Calc old data need remove keys and remove
        final List<Object> minusKeys = new ArrayList<>();
        minusKeys.addAll(oldKeys);
        minusKeys.removeAll(newKeys);
        final byte[] keyBytes = serializer.serialize(key);
        final byte[][] minusArr = serializeCollection(serializer, minusKeys);

        // Have old data
        stringRedisTemplate.executePipelined(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                if (isNotEmpty(minusArr)) {
                    connection.hDel(keyBytes, minusArr);
                }

                // Do put new data
                Map<byte[], byte[]> data = serializeMap(serializer, newMap);
                connection.hMSet(keyBytes, data);

                if (mExpire != -1) {
                    connection.pExpire(keyBytes, mExpire);
                }

                return null;
            }
        });
    }

    public static void addDataForSet(String key,Collection<String> data){
    	
    	if(data!=null && !data.isEmpty()){
    		
        	String[] dataStrArrary = new String[data.size()];
        	
        	stringRedisTemplate.opsForSet().add(key, data.toArray(dataStrArrary));
    	}
    }
    
    public static void removeDataForSet(String key,Collection<String> data){
    	if(data!=null && !data.isEmpty()){

    		stringRedisTemplate.opsForSet().remove(key, data.toArray());
    	}
    }
    
    public static void removeSet(String key){
//    	mExpire(key,50);
    	stringRedisTemplate.delete(key);
    }
    
    public static void removeKey(String key){
//    	mExpire(key,50);
        stringRedisTemplate.delete(key);
    }
    
    public static void removeHash(String key){
//    	mExpire(key,50);
        stringRedisTemplate.delete(key);
    }
    
    public static  Set<String> union(Collection<String> keys){
    	if(keys == null || keys.isEmpty()){
    		return Collections.emptySet();
    	}
    	
    	String key = keys.iterator().next();
    	
    	keys.remove(key);
    	
      	return stringRedisTemplate.opsForSet().union(key, keys);
    }
    
    public static Set<String> intersect(String key1,String key2){
  
    	return stringRedisTemplate.opsForSet().intersect(key1, key2);
    }
    
    private static <T> boolean isNotEmpty(Collection<T> coll) {
        return coll != null && !coll.isEmpty();
    }

    private static boolean isNotEmpty(byte[][] arr) {
        return arr != null && arr.length != 0;
    }

    public static <T> byte[][] serializeCollection(RedisSerializer serializer, Collection<T> coll) {
        byte[][] result;
        if (coll == null || coll.isEmpty()){
            result = new byte[0][];
        }else {
            result = new byte[coll.size()][];
            int count = 0;
            for (T obj : coll) {
                result[count++] = serializer.serialize(obj);
            }
        }
        return result;
    }

    public static <K, V> Map<byte[], byte[]> serializeMap(RedisSerializer serializer, Map<K, V> map) {
        Map<byte[], byte[]> result;
        if (map == null || map.isEmpty()){
            result = new LinkedHashMap<>(0);
        }else {
            result = new LinkedHashMap<>(map.size());
            for (K key : map.keySet()) {
                V val = map.get(key);
                result.put(serializer.serialize(key), serializer.serialize(val));
            }
        }
        return result;
    }

    private static void expire(final Collection<ExpireKey> keys,final TimeUnit timeUnit) {
        if (!isNotEmpty(keys)) { return; }
        final RedisSerializer<String> serializer = stringRedisTemplate.getStringSerializer();
        stringRedisTemplate.executePipelined(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                for (ExpireKey key : keys) {
                    if (key == null) { continue; }
                    String keyKey = key.getKey();
                    long expire = key.getExpire();
                    if (keyKey == null || keyKey.trim().length() == 0) { continue; }
                    byte[] keyBytes = serializer.serialize(keyKey);
                    if(timeUnit == TimeUnit.SECONDS){
                    	 connection.expire(keyBytes, expire);
                    }else if(timeUnit == TimeUnit.MILLISECONDS){
                    	connection.pExpire(keyBytes, expire);
                    }
                   
                }
                return null;
            }
        });
    }


}
