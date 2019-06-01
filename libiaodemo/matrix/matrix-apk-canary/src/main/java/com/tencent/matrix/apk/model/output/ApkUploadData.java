package com.tencent.matrix.apk.model.output;

import java.util.List;

/**
 * Description:
 * Data：2019/4/9-下午8:47
 * Author: libiao
 */
public class ApkUploadData {
    public String pid;
    public String did;
    public String os;
    public String eid;
    public String ver;
    public String verCode;
    public DATA content;
    public String packageName;

    public ApkUploadData() {
        pid = "10000";
        os = "Android";
        eid = "package-size";
        content = new DATA();
    }

    public static class DATA {
        public long totalSize;
        public List<CountInfo> classCount;
        public List<CategoriesInfo> categories;
        public List<PngNoAlpInfo> pngNoAlp;
        public List<String> noCompress;
        public List<DupFileInfo> dupFile;
        public List<String> unusedRes;
        public List<String> unstrip;
        public List<LmtFilesInfo> lmtFiles;
        public List<CountInfo> medCount;
        public List<String> abi;
        public List<String> stlLib;

    }

    public static class CountInfo {
        public String name;
        public long count;
    }

    public static class CategoriesInfo {
        public String name;
        public long size;
    }

    public static class PngNoAlpInfo {
        public String name;
        public long size;
    }

    public static class DupFileInfo {
        public String md5;
        public long size;
        public List<String> files;
    }

    public static class LmtFilesInfo {
        public String md5;
        public long size;
        public String name;
    }

}
