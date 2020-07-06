package com.ywmoyu.tool.Model;

import lombok.Data;

import java.util.List;

@Data
public class UserInfo {
    private String id;
    private String userName;
    private String passWord;
    private String eMail;
    private List<OperateLog> logList;
}
