package com.wzj.util;  
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.tags.PermissionTag;  

public class HasAnyPermissionTag extends PermissionTag {  
    private static final long serialVersionUID = 1L;  
    private static final String PERMISSION_NAMES_DELIMETER = ",";  
    public HasAnyPermissionTag() {}  
    @Override  
    protected boolean showTagBody(String permissions) {  
        boolean hasAnyPermission = false;  
        Subject subject = getSubject();  
        if (subject != null) {  
            for (String permission : permissions  
                    .split(PERMISSION_NAMES_DELIMETER)) {  
                if (subject.isPermitted(permission.trim())) {//trim去掉字符串两头多余的空格  
                    hasAnyPermission = true;  
                    break;  
                }  
            }  
        }  
        return hasAnyPermission;  
    }  
}  