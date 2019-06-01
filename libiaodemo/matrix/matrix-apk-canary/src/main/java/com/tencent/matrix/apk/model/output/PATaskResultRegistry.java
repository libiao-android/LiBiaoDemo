package com.tencent.matrix.apk.model.output;

import com.tencent.matrix.apk.model.result.TaskHtmlResult;
import com.tencent.matrix.apk.model.result.TaskJsonResult;
import com.tencent.matrix.apk.model.result.TaskResultRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * Data：2019/4/7-下午9:56
 * Author: libiao
 */
public class PATaskResultRegistry extends TaskResultRegistry {

    @Override
    public Map<String, Class<? extends TaskHtmlResult>> getHtmlResult() {
        return null;
    }

    @Override
    public Map<String, Class<? extends TaskJsonResult>> getJsonResult() {
        Map<String, Class<? extends TaskJsonResult>> map = new HashMap<>();
        map.put("pa", PATaskJsonResult.class);
        return map;
    }
}
