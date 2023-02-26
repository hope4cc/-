package com.paopao.util;

import com.paopao.exception.GlobalException;
import com.paopao.logger.SysLogger;
import org.springframework.util.StringUtils;

public class Base64Util {

    private String base64;
    private String suffix;
    private String data;

    public Base64Util(String base64) throws GlobalException {
        this.base64 = base64;
        availableCheck(base64);
        computeSuffixAndData(base64);
    }

    public boolean availableCheck(String base64){
        SysLogger.info("正在检查base64数据完整性");
        if(StringUtils.isEmpty(base64)){
            return false;
        }else{
            String [] d = base64.split("base64,");
            if(d.length == 2){
                return true;
            }else{
                return false;
            }
        }
    }


    public void computeSuffixAndData(String base64) throws GlobalException {
        SysLogger.info("正在根据base64数据获取文件类型后缀");
        String dataPrix = "";
        String [] d = base64.split("base64,");
        if(d.length == 2){
            dataPrix = d[0];
            data = d[1];
        }
        if("data:image/jpeg;".equalsIgnoreCase(dataPrix)){//data:image/jpeg;base64,base64编码的jpeg图片数据
            suffix = ".jpg";
        } else if("data:image/x-icon;".equalsIgnoreCase(dataPrix)){//data:image/x-icon;base64,base64编码的icon图片数据
            suffix = ".ico";
        } else if("data:image/gif;".equalsIgnoreCase(dataPrix)){//data:image/gif;base64,base64编码的gif图片数据
            suffix = ".gif";
        } else if("data:image/png;".equalsIgnoreCase(dataPrix)){//data:image/png;base64,base64编码的png图片数据
            suffix = ".png";
        }else{
            throw new GlobalException("上传图片格式不合法");
        }

        SysLogger.info("上传的图片类型为:" + suffix);


    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
