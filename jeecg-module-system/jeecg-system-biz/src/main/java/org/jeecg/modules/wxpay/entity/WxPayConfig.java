package org.jeecg.modules.wxpay.entity;

import com.github.wxpay.sdk.WXPayConfig;

import java.io.InputStream;

/**
 * @author QiaoQi
 * @version 1.0
 */
public class WxPayConfig implements WXPayConfig {
    @Override
    public String getAppID() {
        return "wx1008779fb8e3422a";
    }

    @Override
    public String getMchID() {
        return "1649387434";
    }

    @Override
    public String getKey() {
        return "96a7d88a80290d602a2b6bfd5d179599";
    }

    @Override
    public InputStream getCertStream() {
        return null;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 3000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 3000;
    }
}
