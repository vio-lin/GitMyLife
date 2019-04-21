package com.violin.rpc.common;


import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author lin
 * Date: 2019-04-21
 * 稍微参考下dubbo 主要是地址端口 加 interface 和参数就好
 */
public class RpcURL {
    private String protocol;
    private String host;
    private int port;
    private String interfaceName;
    private Map<String, String> parameter;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

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

    public Map<String, String> getParameter() {
        return parameter;
    }

    public void setParameter(Map<String, String> parameter) {
        this.parameter = parameter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RpcURL rpcURL = (RpcURL) o;
        return port == rpcURL.port &&
                Objects.equals(protocol, rpcURL.protocol) &&
                Objects.equals(host, rpcURL.host) &&
                Objects.equals(interfaceName, rpcURL.interfaceName) &&
                Objects.equals(parameter, rpcURL.parameter);
    }

    @Override
    public int hashCode() {

        return Objects.hash(protocol, host, port, interfaceName, parameter);
    }

    /**
     * 解析String对对应的数据
     *
     * @param url
     * @return
     */
    public static RpcURL valueOf(String url) {
        RpcURL rpcURL = new RpcURL();
        //协议 "://"+ Host + ":"+port+"/InsterfaceClass?Paramkey=ParameteValue&ParameteKey=ParameterValue2"
        int protocolEnd = url.indexOf("://");
        validateField(url, protocolEnd, "protocol");
        String protocol = url.substring(0, url.indexOf("://"));
        rpcURL.setProtocol(protocol);

        int hostEnd = url.indexOf(":", protocolEnd + 3);
        validateField(url, protocolEnd, "host");
        String hostName = url.substring(protocolEnd + 3, hostEnd);
        rpcURL.setHost(hostName);

        int portEnd = url.indexOf("/", hostEnd + 3);
        validateField(url, portEnd, "port");
        String portString = url.substring(hostEnd + 1, portEnd);
        rpcURL.setPort(Integer.parseInt(portString));

        int interfaceEnd = url.indexOf("?", portEnd);
        validateField(url, interfaceEnd, "interface");
        String interfaceName = url.substring(portEnd + 1, interfaceEnd);
        rpcURL.setInterfaceName(interfaceName);


        String parameterStr = url.substring(interfaceEnd + 1);
        Map<String, String> parameter;
        try {
            parameter = parseParameterMap(parameterStr);
        } catch (Exception e) {
            throw new IllegalArgumentException("解析域名中的参数失败:" + url);
        }
        rpcURL.setParameter(parameter);
        return rpcURL;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Preconditions.checkNotNull(protocol);
        Preconditions.checkNotNull(host);
        Preconditions.checkNotNull(port);
        Preconditions.checkNotNull(interfaceName);
        Preconditions.checkNotNull(parameter);

        sb.append(protocol).append("://");
        sb.append(host).append(":").append(port).append("/");
        sb.append(interfaceName).append("?");
        String parameterString = getParameterString(parameter);
        sb.append(parameterString);
        return sb.toString();
    }

    private String getParameterString(Map<String, String> parameter) {
        if (parameter.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : parameter.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        return sb.substring(0, sb.length() - 1);
    }

    private static Map<String, String> parseParameterMap(String parameterStr) {
        Map<String, String> parameter = new HashMap<>();
        String[] paramEntryArray = parameterStr.split("&");
        for (String parameterEntry : paramEntryArray) {
            String[] kv = parameterEntry.split("=");
            parameter.put(kv[0], kv[1]);
        }
        return parameter;
    }

    private static void validateField(String url, int protocolEnd, String string) {
        if (protocolEnd == -1) {
            throw new IllegalArgumentException("找不到解析字符串中的" + string + "字段:" + url);
        }
    }
}
