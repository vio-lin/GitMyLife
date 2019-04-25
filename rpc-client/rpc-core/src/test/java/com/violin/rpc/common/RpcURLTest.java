package com.violin.rpc.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lin
 * Date: 2019-04-21
 */
@RunWith(JUnit4.class)
public class RpcURLTest {
    @Test
    public void testGenerateToString(){
        String protocol = "someProtocol";
        String host = "someHost";
        int port = 8080;
        String interfaceName = "someInterface";
        String someKey = "someKey";
        String someValue = "someValue";
        Map<String,String> param = new HashMap();
        param.put(someKey,someValue);

        RpcURL url = new RpcURL();
        url.setProtocol(protocol);
        url.setParameter(param);
        url.setHost(host);
        url.setParameter(param);
        url.setInterfaceName(interfaceName);
        url.setPort(port);

        String expectUrlStr = protocol+"://"+host+":"+port+"/"+interfaceName+"?"+someKey+"="+someValue;
        assertEquals(expectUrlStr,url.toString());
    }

    @Test
    public void testValueOf(){
        String protocol = "someProtocol";
        String host = "someHost";
        int port = 8080;
        String interfaceName = "someInterface";
        String someKey = "someKey";
        String someValue = "someValue";
        Map<String,String> param = new HashMap();
        param.put(someKey,someValue);

        RpcURL url = new RpcURL();
        url.setProtocol(protocol);
        url.setParameter(param);
        url.setHost(host);
        url.setParameter(param);
        url.setInterfaceName(interfaceName);
        url.setPort(port);

        String urlStr = protocol+"://"+host+":"+port+"/"+interfaceName+"?"+someKey+"="+someValue;
        RpcURL actualUrl = RpcURL.valueOf(urlStr);
        assertEquals(url,actualUrl);
    }

}
