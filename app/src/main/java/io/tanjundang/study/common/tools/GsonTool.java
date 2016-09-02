package io.tanjundang.study.common.tools;

import com.google.gson.Gson;

/**
 * Developer: TanJunDang
 * Email: TanJunDang324@gmail.com
 */
public class GsonTool {

    private static Gson gson = new Gson();

    /**
     * @param json
     * @param cls
     * @param <T>  表示通用类型 后面接一个字母表示对象
     * @return将String类型转成对象
     */
    public static <T> T getServerBean(String json, Class<?> cls) {
        T object = null;
        if (gson == null) {
            gson = new Gson();
        }
        object = (T) gson.fromJson(json, cls);
        return object;
    }


    /**
     *
     * @param obj
     * @return
     * 将对象转成json（String）
     */
    public static String getObjectToJson(Object obj) {
        String json = "";
        if (gson == null) {
            gson = new Gson();
        }
        json = gson.toJson(obj);
        return json;
    }
}
