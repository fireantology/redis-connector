package org.mule.module.redis.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.components.Configuration;
import org.mule.api.annotations.display.Placement;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;
import org.mule.api.store.ObjectStore;
import org.mule.api.store.PartitionableObjectStore;

import redis.clients.jedis.JedisPoolConfig;

@Configuration(friendlyName = "Configuration")
public class ConnectorConfig {
	
	 /**
     * Redis host.
     */
    @Configurable
    @Optional
    @Placement(order = 0, group = "General", tab = "Single instance")
    @Default("localhost")
    private String host;

    /**
     * Redis port.
     */
    @Configurable
    @Optional
    @Placement(order = 0, group = "General", tab = "Single instance")
    @Default("6379")
    private int port;

    /**
     * Connection timeout in milliseconds.
     */
    @Configurable
    @Optional
    @Default("2000")
    private int connectionTimeout;

    /**
     * Reconnection frequency in milliseconds.
     */
    @Configurable
    @Optional
    @Default("5000")
    private int reconnectionFrequency;

    /**
     * Redis password
     */
    @Configurable
    @Optional
    private String password;

    /**
     * Object pool configuration.
     */
    @Configurable
    @Optional
    private GenericObjectPoolConfig poolConfig = new JedisPoolConfig();

    /**
     * The {@link PartitionableObjectStore} partition to use in case methods from
     * {@link ObjectStore} are used.
     */
    @Configurable
    @Optional
    private String defaultPartitionName;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public int getReconnectionFrequency() {
		return reconnectionFrequency;
	}

	public void setReconnectionFrequency(int reconnectionFrequency) {
		this.reconnectionFrequency = reconnectionFrequency;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public GenericObjectPoolConfig getPoolConfig() {
		return poolConfig;
	}

	public void setPoolConfig(GenericObjectPoolConfig poolConfig) {
		this.poolConfig = poolConfig;
	}

	public String getDefaultPartitionName() {
		return defaultPartitionName;
	}

	public void setDefaultPartitionName(String defaultPartitionName) {
		this.defaultPartitionName = defaultPartitionName;
	}

}
