package com.ihsan.webservice.dto;

import javax.ws.rs.HeaderParam;

import org.apache.commons.lang3.StringUtils;

public class ServiceInput {
    @HeaderParam("securityKey")
    public String securityKey;
    @HeaderParam("securityPart")
    public String securityPart;
    @HeaderParam("lang")
    public String lang;

    public String getSecurityKey() {
        return securityKey;
    }

    public void setSecurityKey(String securityKey) {
        this.securityKey = securityKey;
    }

    public String getSecurityPart() {
        return securityPart;
    }

    public void setSecurityPart(String securityPart) {
        this.securityPart = securityPart;
    }

    public String getLang() {
        if (StringUtils.isEmpty(lang))
            return "ar";
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
