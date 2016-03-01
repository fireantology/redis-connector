Mule Redis Connector
====================

Provides Redis connectivity to Mule:

- Supports [Redis Publish/Subscribe model](http://redis.io/topics/pubsub) for asynchronous message exchanges,
- Allows direct reading and writing operations in Redis collections,  
- Allows using Redis as a datastore for Mule components that require persistence.

Build Commands
--------------

To compile and install in the local Maven repository:

    mvn clean install  

If you have Redis running on localhost:6379, you can run the integration tests with:

    mvn -Pit verify


Features
--------

### Configuration

Connecting to a local Redis with no password and default connection pooling is as simple as:

    <redis:config />
    
If you need to refer to this configuration (in case you have several different connections to different Redis servers or if you need to inject it), then you'll have to name it:

    <redis:config name="localRedis" />

The following demonstrates one example of advanced configuration:

    <spring:beans>
        <spring:bean name="redisPoolConfiguration" class="org.apache.commons.pool2.impl.GenericObjectPoolConfig"
            p:blockWhenExhausted="true" />
    </spring:beans>
    
    <redis:config name="localRedis"
                  host="localhost"
                  port="6379"
                  password="s3cre3t"
                  connectionTimeout="15000">
         <redis:pool-config ref="redisPoolConfiguration"/>
     </redis:config>
     
If you want to connect to Sentinel to support Master/Slave Redis configuration you can do it in the following way

    <redis:config name="localRedis" connectionTimeout="15000">
        <redis:sentinels>
            <redis:sentinel>127.0.0.1:5001</redis:sentinel>
            <redis:sentinel>127.0.0.1:5002</redis:sentinel>
            <redis:sentinel>127.0.0.1:5003</redis:sentinel>
        </redis:sentinels>
    </redis:config>

### Datastructure Operations

This module allows your Mule flows to interact with the main Redis datastructures: [strings](http://redis.io/commands#string), [hashes](http://redis.io/commands#hash), [lists](http://redis.io/commands#list), [sets](http://redis.io/commands#set) and [sorted sets](http://redis.io/commands#sorted_set).

> Not all commands available on these datastructures are exposed: let us know if you're missing a command and we'll add it!

#### Strings

Storing the current payload under the specified key can be done with different options:

    <redis:set key="my_key" />
    <redis:set key="my_key" expire="3600" />
    <redis:set key="my_key" ifNotExists="true" />

Retrieving is done with:

    <redis:get key="my_key" />

#### Hashes

Storing the current payload under the specified key and field can be done with different options:

    <redis:hash-set key="my_key" field="my_field" />
    <redis:hash-set key="my_key" field="my_field" ifNotExists="true" />

Retrieving is done with:

    <redis:hash-get key="my_key" field="my_field" />
    
#### Lists

Pushing the current payload under the specified key can be done with different options:

    <redis:list-push key="my_key" side="LEFT"/>
    <redis:list-push key="my_key" side="RIGHT" />
    <redis:list-push key="my_key" side="LEFT" ifExists="true" />
    <redis:list-push key="my_key" side="RIGHT" ifExists="true" />

Popping is done with either:

    <redis:list-pop key="my_key" side="LEFT" />
    <redis:list-pop key="my_key" side="RIGHT" />

#### Sets

Adding the current payload under to the set at the specified key can be done with different options:

    <redis:set-add key="my_key" />
    <redis:set-add key="my_key" mustSucceed="true" />

Retrieving is done with either:

    <redis:set-fetch-random-member key="my_key" />
    <redis:set-pop key="my_key" />

#### Sorted Sets

Adding the current payload under to the set at the specified key can be done with different options:

    <redis:sorted-set-add key="my_key" score="123" />
    <redis:sorted-set-add key="my_key" score="123" mustSucceed="true" />

Retrieving is done with either:

    <redis:sorted-set-select-range-by-index key="my_key" start="0" end="-1" />
    <redis:sorted-set-select-range-by-index key="my_key" start="0" end="-1" order="DESCENDING" />
    <redis:sorted-set-select-range-by-score key="my_key" min="0.5" max="10" />
    <redis:sorted-set-select-range-by-score key="my_key" min="10" max="0.5" order="DESCENDING" />

### Publish/Subscribe

Publishing to a Redis channel is achieved as shown here after:

    <redis:publish channel="news.art.figurative" />

Any message hitting this [message processor](http://www.mulesoft.org/documentation/display/MULE3USER/Message+Sources+and+Message+Processors#MessageSourcesandMessageProcessors-MessageProcessors) will be transformed into a byte array (using Mule's transformation infrastructure) and will be published to the `news.art.figurative` channel.

Subscribing to a channel is done by specifying names or patterns to which Mule will listen. For example, the following subscribes to a channel named `news.sport.hockey` and any channel that matches the `news.art.*` globbing pattern.

    <redis:subscribe>
        <redis:channels>
            <redis:channel>news.sport.hockey</redis:channel>
            <redis:channel>news.art.*</redis:channel>
        </redis:channels>
    </redis:subscribe>

This can be used as a [message source](http://www.mulesoft.org/documentation/display/MULE3USER/Message+Sources+and+Message+Processors#MessageSourcesandMessageProcessors-MessageSources) in any flow. 


### Object Store

The configured Redis module can act as an [ObjectStore](http://www.mulesoft.org/docs/site/current3/apidocs/index.html?org/mule/api/store/ObjectStore.html), which can be injected into any object needing such a store.

> Mule object stores are stored as Redis Hashes named `mule.objectstore.{ospn}`, where ospn is the object store partition name (or `_default` if none has been specified). Values are stored as Java-serialized bytes, except if they are strings. In that case, the string is stored as-is. 

For example, the following shows how to use the Redis module as the data store for a Mule-powered [PubSubHubbub hub](https://github.com/mulesoft/mule-module-pubsubhubbub):

    <redis:config name="localRedis" />
    <pubsubhubbub:config objectStore-ref="localRedis" />
   
When using the Redis module has object store you can also specify a custom partition name and an expiration time in seconds that are useful when doing enterprise caching

    <redis:config name="localRedis" partitionExpiry="5" defaultPartitionName="mypartition"/>

