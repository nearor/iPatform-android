package com.nearor.framwork.network;





import com.nearor.framwork.ValueObject;
import com.nearor.framwork.util.URLUtil;

import java.util.ArrayList;

/**
 *
 */
public class UploadResponse extends ValueObject {

    public static final String IMAGE_UPLOAD_RESPONSE_SUCCESS_MESSAGE = "success";

    private String msg;
    private String result;

    public boolean isSuccessed() {
        boolean successed = false;
        if (result != null) {
            successed = result.equalsIgnoreCase(IMAGE_UPLOAD_RESPONSE_SUCCESS_MESSAGE);
        }
        return successed;
    }

    /**
     * 获取已上传图片的绝对路径（含 host），如果同时上传多张则取第一张
     * @return 上传图片的绝对路径（例如：http://img.52zhai.com/upload/xxx.jpg）
     */
    public String getAbsolutePath() {
        if (getPath().length() > 0) {
            return URLUtil.getImageBaseUrl() + getPath();
        } else {
            return "";
        }
    }

    /**
     * 获取已上传图片的相对路径（不含 host），如果同时上传了多张则取第一张
     * @return 上传图片相对路径（例如：/upload/xxx.jpg）
     */
    public String getPath() {
        String [] paths = getAllPath();
        if (paths != null && paths.length > 0) {
            return paths[0];
        } else {
            return "";
        }
    }

    /**
     * 获取所有已上传图片的绝对路径 {@link #getAbsolutePath()}
     * @return 包含所有图片路径的数组 or null
     */
    public String[] getAllAbsolutePath() {
        String[] paths = getAllPath();
        if (paths != null && paths.length > 0) {
            ArrayList<String> absolutePaths = new ArrayList<>();
            for (String path : paths) {
                absolutePaths.add(URLUtil.getImageBaseUrl() + path);
            }
            return absolutePaths.toArray(new String[absolutePaths.size()]);
        } else {
            return null;
        }
    }

    /**
     * 获取所有已上传图片的相对路径 {@link #getPath()}
     * @return 包含所有图片路径的数组 or null
     */
    public String[] getAllPath() {
        if (isSuccessed() && msg != null && msg.length() >0) {
            return msg.split(",");
        } else {
            return null;
        }
    }

}
