package com.kitchen.rpc.server.deploy.host.wan.windows;

/**
 * 枚举公网地址查询服务站点
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-04-20
 */
public enum WideAreaNetworkSiteEnum {

    IP138("http://city.ip138.com/ip2city.asp"),
    DYNDNS("http://checkip.dyndns.org/");

    private String _url;

    WideAreaNetworkSiteEnum(String url) {
        this._url = url;
    }

    public String getUrl() {
        return this._url;
    }

    @Override
    public String toString() {
        return String.valueOf(this._url);
    }
}
