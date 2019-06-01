package com.tencent.matrix.apk.model.output;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tencent.matrix.apk.model.result.TaskJsonResult;
import com.tencent.matrix.apk.model.task.TaskFactory;
import com.tencent.matrix.javalib.util.Log;
import com.tencent.matrix.javalib.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Description:
 * Data：2019/4/7-下午10:00
 * Author: libiao
 */
public class PATaskJsonResult extends TaskJsonResult {
    public static ApkUploadData data = new ApkUploadData();

    public PATaskJsonResult(int taskType, JsonObject config) throws ParserConfigurationException {
        super(taskType, config);
    }

    @Override
    public void format(JsonObject jsonObject) {
        int taskType = jsonObject.get("taskType").getAsInt();
        switch (taskType) {
            case TaskFactory.TASK_TYPE_UNZIP:
                parseCategoriesData(jsonObject);
                break;
            case TaskFactory.TASK_TYPE_MANIFEST:
                parseVersiontData(jsonObject);
                break;
            case TaskFactory.TASK_TYPE_SHOW_FILE_SIZE:
                parseLmtFileSizeData(jsonObject);
                break;
            case TaskFactory.TASK_TYPE_COUNT_METHOD:
                parseCountData(jsonObject);
                break;
            case TaskFactory.TASK_TYPE_FIND_NON_ALPHA_PNG:
                parseNoAlpPngData(jsonObject);
                break;
            case TaskFactory.TASK_TYPE_CHECK_MULTILIB:
                parseMultilibData(jsonObject);
                break;
            case TaskFactory.TASK_TYPE_UNCOMPRESSED_FILE:
                parseNoCompressFile(jsonObject);
                break;
            case TaskFactory.TASK_TYPE_DUPLICATE_FILE:
                parseDupFile(jsonObject);
                break;
            case TaskFactory.TASK_TYPE_CHECK_MULTISTL:
                parseMultiStl(jsonObject);
                break;
            case TaskFactory.TASK_TYPE_UNUSED_RESOURCES:
                parseUnusedRes(jsonObject);
                break;
            case TaskFactory.TASK_TYPE_UNUSED_ASSETS:
                parseUnusedAssets(jsonObject);
                break;
            case TaskFactory.TASK_TYPE_UNSTRIPPED_SO:
                parseUnstripSo(jsonObject);
                break;
            case TaskFactory.TASK_TYPE_COUNT_CLASS:
                parseClassCount(jsonObject);
                break;
        }
        if (this.jsonObject != null) {
            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                if (!this.jsonObject.has(entry.getKey())) {
                    this.jsonObject.add(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    private void parseClassCount(JsonObject jsonObject) {
        data.content.classCount = new ArrayList<>();
        JsonArray dexFiles = jsonObject.getAsJsonArray("dex-files");
        for (JsonElement entry : dexFiles) {
            JsonObject dexFile = entry.getAsJsonObject();
            JsonArray pkgs = dexFile.get("packages").getAsJsonArray();
            for (JsonElement pkg : pkgs) {
                JsonObject pkgObj = pkg.getAsJsonObject();
                String pkgName = pkgObj.get("package").getAsString();
                if(pkgName.startsWith(data.packageName == null ? "" : data.packageName)) {
                    ApkUploadData.CountInfo info = new ApkUploadData.CountInfo();
                    info.name = pkgName;
                    JsonArray classes = pkgObj.getAsJsonArray("classes");
                    info.count = classes.size();
                    data.content.classCount.add(info);
                }
            }
        }
    }

    private void parseUnstripSo(JsonObject jsonObject) {
        data.content.unstrip = new ArrayList<>();
        JsonArray soArray = jsonObject.getAsJsonArray("unstripped-lib");
        for (JsonElement so : soArray) {
            data.content.unstrip.add(so.getAsString());
        }
    }

    private void parseUnusedAssets(JsonObject jsonObject) {
        if(data.content.unusedRes == null) data.content.unusedRes = new ArrayList<>();
        JsonArray res = jsonObject.getAsJsonArray("unused-assets");
        for (JsonElement r : res) {
            data.content.unusedRes.add(r.getAsString());
        }
    }

    private void parseUnusedRes(JsonObject jsonObject) {
        if(data.content.unusedRes == null) data.content.unusedRes = new ArrayList<>();
        JsonArray res = jsonObject.getAsJsonArray("unused-resources");
        for (JsonElement r : res) {
            data.content.unusedRes.add(r.getAsString());
        }
    }

    private void parseMultiStl(JsonObject jsonObject) {
        data.content.stlLib = new ArrayList<>();
        JsonArray stlLibs = jsonObject.getAsJsonArray("stl-lib");
        for (JsonElement lib : stlLibs) {
            data.content.stlLib.add(lib.getAsString());
        }
    }

    private void parseDupFile(JsonObject jsonObject) {
        data.content.dupFile = new ArrayList<>();
        JsonArray files = jsonObject.getAsJsonArray("files");
        for (JsonElement file : files) {
            ApkUploadData.DupFileInfo info = new ApkUploadData.DupFileInfo();
            info.md5 = ((JsonObject) file).get("md5").getAsString();
            info.size = ((JsonObject) file).get("size").getAsLong();
            info.files = new ArrayList<>();
            JsonArray array = ((JsonObject) file).getAsJsonArray("files");
            for(JsonElement value : array) {
                info.files.add(value.getAsString());
            }
            data.content.dupFile.add(info);
        }
    }

    private void parseNoCompressFile(JsonObject jsonObject) {
        data.content.noCompress = new ArrayList<>();
        JsonArray files = jsonObject.getAsJsonArray("files");
        for (JsonElement file : files) {
            data.content.noCompress.add(file.getAsString());
        }
    }

    private void parseMultilibData(JsonObject jsonObject) {
        data.content.abi = new ArrayList<>();
        JsonArray libs = jsonObject.getAsJsonArray("lib-dirs");
        for (JsonElement lib : libs) {
            data.content.abi.add(lib.getAsString());
        }
    }

    private void parseNoAlpPngData(JsonObject jsonObject) {
        data.content.pngNoAlp = new ArrayList<>();
        JsonArray files = jsonObject.getAsJsonArray("files");
        for (JsonElement file : files) {
            ApkUploadData.PngNoAlpInfo info = new ApkUploadData.PngNoAlpInfo();
            info.name = ((JsonObject) file).get("entry-name").getAsString();
            info.size = ((JsonObject) file).get("entry-size").getAsLong();
            data.content.pngNoAlp.add(info);
        }
    }

    private void parseCountData(JsonObject jsonObject) {
        data.content.medCount = new ArrayList<>();
        JsonArray dexFiles = jsonObject.getAsJsonArray("dex-files");
        for (JsonElement entry : dexFiles) {
            JsonObject dexFile = entry.getAsJsonObject();
            JsonArray defGroups = null;
            if (dexFile.has("internal-packages")) {
                defGroups = dexFile.getAsJsonArray("internal-packages");
                addMedCountInfo(defGroups);
            }
            if (dexFile.has("external-packages")) {
                defGroups = dexFile.getAsJsonArray("internal-classes");
                addMedCountInfo(defGroups);
            }
        }
    }

    private void addMedCountInfo(JsonArray defGroups) {
        if(defGroups == null) return;
        for(JsonElement countInfo :  defGroups) {
            String packageName = ((JsonObject)countInfo).get("name").getAsString();
            if(packageName.startsWith(data.packageName == null ? "" : data.packageName)) {
                ApkUploadData.CountInfo info = new ApkUploadData.CountInfo();
                info.name = packageName;
                info.count = ((JsonObject)countInfo).get("methods").getAsLong();
                data.content.medCount.add(info);
            }
        }
    }

    private void parseLmtFileSizeData(JsonObject jsonObject) {
        data.content.lmtFiles = new ArrayList<>();
        JsonArray files = jsonObject.getAsJsonArray("files");
        for (JsonElement file : files) {
            ApkUploadData.LmtFilesInfo info = new ApkUploadData.LmtFilesInfo();
            info.name = ((JsonObject) file).get("entry-name").getAsString();
            info.size = ((JsonObject) file).get("entry-size").getAsLong();
            data.content.lmtFiles.add(info);
        }
    }

    private void parseVersiontData(JsonObject jsonObject) {
        JsonObject manifest = jsonObject.getAsJsonObject("manifest");
        if (manifest.has("android:versionCode")) {
            data.verCode = manifest.get("android:versionCode").getAsString();
        }
        if (manifest.has("android:versionName")) {
            data.ver = manifest.get("android:versionName").getAsString();
        }
        if (manifest.has("package")) {
            data.packageName = manifest.get("package").getAsString();
        }
    }

    private void parseCategoriesData(JsonObject jsonObject) {
        long minFileSize = jsonObject.get("min").getAsLong();
        data.content.totalSize = jsonObject.get("total-size").getAsLong();
        Map<String, Long> fileGroupMap = new HashMap<>();
        long otherFilesSize = 0;
        JsonArray files = jsonObject.getAsJsonArray("entries");
        for (JsonElement file : files) {
            final String filename = ((JsonObject) file).get("entry-name").getAsString();
            if (!Util.isNullOrNil(filename)) {
                int index = filename.lastIndexOf('.');
                if (index >= 0) {
                    final String suffix = filename.substring(index);
                    if (!fileGroupMap.containsKey(suffix)) {
                        fileGroupMap.put(suffix, ((JsonObject) file).get("entry-size").getAsLong());
                    } else {
                        fileGroupMap.put(suffix, fileGroupMap.get(suffix) + ((JsonObject) file).get("entry-size").getAsLong());
                    }
                } else {
                    otherFilesSize += ((JsonObject) file).get("entry-size").getAsLong();
                }
            }
        }
        data.content.categories = new ArrayList<>();
        for (Map.Entry<String, Long> entry : fileGroupMap.entrySet()) {
            if(entry.getValue() > minFileSize) {
                ApkUploadData.CategoriesInfo info = new ApkUploadData.CategoriesInfo();
                info.name = entry.getKey();
                info.size = entry.getValue();
                data.content.categories.add(info);
            }
        }
        if(otherFilesSize > minFileSize) {
            ApkUploadData.CategoriesInfo info = new ApkUploadData.CategoriesInfo();
            info.name = "other";
            info.size = otherFilesSize;
            data.content.categories.add(info);
        }
    }
}
