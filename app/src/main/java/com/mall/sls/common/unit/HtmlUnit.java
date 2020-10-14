package com.mall.sls.common.unit;

public class HtmlUnit {
    public static String getHtmlData(String bodyHTML) {
        String head = "<head>"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> "
                + "<style>video{max-width: 100%; width:100%; height:auto;}img{max-width: 100%; width:100%; height:auto;vertical-align:top;}*{margin-left: 0px;margin-right: 0px;}</style>"
                + "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }
}