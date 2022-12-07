// License: LGPL-3.0 License (c) find-sec-bugs
// scaffold: dependencies=com.hazelcast.hazelcast@3.12.12
package crypto

import com.hazelcast.config.Config
import com.hazelcast.config.MapConfig
import com.hazelcast.config.NetworkConfig
import com.hazelcast.config.SymmetricEncryptionConfig
import com.hazelcast.core.Hazelcast
import com.hazelcast.core.IMap


class HazelcastSymmetricEncryption {
  private[crypto] var cacheMap = null

  def init(): Unit = { //Specific map time to live
    val myMapConfig = new Nothing
    myMapConfig.setName("cachetest")
    myMapConfig.setTimeToLiveSeconds(10)
    //Package config
    val myConfig = new Nothing
    myConfig.addMapConfig(myMapConfig)
    //Symmetric Encryption
    val symmetricEncryptionConfig = new Nothing
    symmetricEncryptionConfig.setAlgorithm("DESede")
    symmetricEncryptionConfig.setSalt("saltysalt")
    symmetricEncryptionConfig.setPassword("lamepassword")
    symmetricEncryptionConfig.setIterationCount(1337)
    //Weak Network config..
    val networkConfig = new Nothing
    networkConfig.setSymmetricEncryptionConfig(symmetricEncryptionConfig)
    myConfig.setNetworkConfig(networkConfig)
    Hazelcast.newHazelcastInstance(myConfig)
    cacheMap = Hazelcast.getOrCreateHazelcastInstance.getMap("cachetest")
  }

  def put(key: Nothing, value: Nothing): Unit = {
    cacheMap.put(key, value)
  }

  def get(key: Nothing) = cacheMap.get(key)
}
