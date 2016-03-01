package org.mule.module.redis.config;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.components.Configuration;
import org.mule.api.annotations.display.Placement;
import org.mule.api.annotations.display.Summary;
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
    @Default("localhost")
    @Placement(order = 0, group = "Connection")
    private String host;

    /**
     * Redis port.
     */
    @Configurable
    @Default("6379")
    @Placement(order = 1, group = "Connection")
    private int port;

    /**
     * Connection timeout in milliseconds.
     */
    @Configurable
    @Default("2000")
    @Placement(order = 3, group = "Connection")
    private int connectionTimeout;

    /**
     * Reconnection frequency in milliseconds.
     */
    @Configurable
    @Default("5000")
    @Placement(order = 7, group = "Publish/Subscribe")
    private int reconnectionFrequency;

    /**
     * Redis password
     */
    @Configurable
    @Optional
    @Placement(order = 2, group = "Connection")
    private String password;

    /**
     * Object pool configuration.
     */
    @Configurable
    @Optional
    @Placement(order = 5, group = "Connection")
    private GenericObjectPoolConfig poolConfig = new JedisPoolConfig();

    /**
     * The {@link PartitionableObjectStore} partition to use in case methods from
     * {@link ObjectStore} are used.
     */
    @Configurable
    @Optional
    @Placement(order = 0, group = "General", tab = "ObjectStore")
    private String defaultPartitionName;
    
	/**
	 * The expiration of the {@link ObjectStore} cache partition in seconds
	 */
    @Configurable
    @Optional
    @Summary("The expiration time of the partition in seconds. O means no expiration")
    @Placement(order = 1, group = "General", tab = "ObjectStore")
	private int partitionExpiry = 0;
    
	/**
	 * The set of sentinel servers to connect to
	 */
    @Configurable
    @Optional
    @Summary("The list of Sentinel servers in the form host:port (127.0.0.1:5000)")
    @Placement(order = 8, group = "Sentinel")
	private Set<String> sentinels = new HashSet<String>();

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

	public int getPartitionExpiry() {
		return partitionExpiry;
	}

	public void setPartitionExpiry(int partitionExpiry) {
		this.partitionExpiry = partitionExpiry;
	}

	public Set<String> getSentinels() {
		return sentinels;
	}

	public void setSentinels(Set<String> sentinels) {
		this.sentinels = sentinels;
	}
	
}
