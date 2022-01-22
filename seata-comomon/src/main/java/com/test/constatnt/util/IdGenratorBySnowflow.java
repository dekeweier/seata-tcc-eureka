package com.test.constatnt.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

/**
 * Create by 李传伟 on 2022/1/8 14:27
 */
@Repository
public class IdGenratorBySnowflow{

        private long workId = 0;
        private long datacenterId = 1;
        private Snowflake snowflake = IdUtil.createSnowflake(workId, 1);

        @PostConstruct
        public void init() {
            workId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());

        }

        public synchronized long snowflakeId() {
            return snowflake.nextId();
        }

        // 范围为0~31
        public synchronized long snowflakeId(long workId, long datacenterId) {
            Snowflake snowflake = IdUtil.createSnowflake(workId, datacenterId);
            return snowflake.nextId();
        }


}
