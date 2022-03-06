package org.hionesoft.crudmaker.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class ServletUtil {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public String getIp(HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");

//        logger.debug(">>>> X-FORWARDED-FOR : " + ip);

        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
//            logger.debug(">>>> Proxy-Client-IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP"); // 웹로직
//            logger.debug(">>>> WL-Proxy-Client-IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
//            logger.debug(">>>> HTTP_CLIENT_IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
//            logger.debug(">>>> HTTP_X_FORWARDED_FOR : " + ip);
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }

        logger.debug(">>>> Result : IP Address : " + ip);

        return ip;

    }


    public String getUseragent(HttpServletRequest request) {

        String userAgent = request.getHeader("user-agent");
        String browser = "";

        if (userAgent.indexOf("Trident") > -1 || userAgent.indexOf("MSIE") > -1) { //IE

            if (userAgent.indexOf("Trident/7") > -1) {
                browser = "IE 11";
            } else if (userAgent.indexOf("Trident/6") > -1) {
                browser = "IE 10";
            } else if (userAgent.indexOf("Trident/5") > -1) {
                browser = "IE 9";
            } else if (userAgent.indexOf("Trident/4") > -1) {
                browser = "IE 8";
            } else if (userAgent.indexOf("edge") > -1) {
                browser = "IE edge";
            }

        } else if (userAgent.indexOf("Whale") > -1) { //네이버 WHALE
            browser = "WHALE " + userAgent.split("Whale/")[1].toString().split(" ")[0].toString();
        } else if (userAgent.indexOf("Opera") > -1 || userAgent.indexOf("OPR") > -1) { //오페라
            if (userAgent.indexOf("Opera") > -1) {
                browser = "OPERA " + userAgent.split("Opera/")[1].toString().split(" ")[0].toString();
            } else if (userAgent.indexOf("OPR") > -1) {
                browser = "OPERA " + userAgent.split("OPR/")[1].toString().split(" ")[0].toString();
            }
        } else if (userAgent.indexOf("Firefox") > -1) { //파이어폭스
            browser = "FIREFOX " + userAgent.split("Firefox/")[1].toString().split(" ")[0].toString();
        } else if (userAgent.indexOf("Safari") > -1 && userAgent.indexOf("Chrome") == -1) { //사파리
            browser = "SAFARI " + userAgent.split("Safari/")[1].toString().split(" ")[0].toString();
        } else if (userAgent.indexOf("Chrome") > -1) { //크롬
            browser = "CHROME " + userAgent.split("Chrome/")[1].toString().split(" ")[0].toString();
        }

        logger.debug("userAgent 확인 [" + userAgent + "]");
        logger.debug("브라우저/버전 확인 [" + browser + "]");

        return browser;
    }

    public String getServerIp() {
        InetAddress local = null;
        try {
            local = InetAddress.getLocalHost();

        } catch ( UnknownHostException e ) {
            e.printStackTrace();
        }

        String ip = "";
        if(!(local == null)) {
            ip = local.getHostAddress();
        }

        logger.debug("서버 IP 확인 [" + ip + "]");

        return ip;
    }
}
