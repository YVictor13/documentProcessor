package com.ywmoyu.tool.model;

import lombok.Data;
import java.util.Date;

@Data
public class OperateLog {
    private String id;
    private String type;  //上传\下载\修改信息
    private String inputPath;
    private String outputPath;
    private String description;
    private Date operateTime;
}
