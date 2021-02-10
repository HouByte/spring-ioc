package cn.bugio.spring.mini.multipart;

import java.util.Arrays;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description 上传文件类
 * @since 2021/1/21
 */

public final class MultipartFile {
    
    private String fileName;
    
    private String fileType;
    
    private byte[] fileData;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    @Override
    public String toString() {
        return "PostFile [fileName=" + fileName + ", fileType=" + fileType + ", fileData=" + Arrays.toString(fileData)
                + "]";
    }

}
