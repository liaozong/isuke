package com.example.liao.isuke.factory;

import java.util.HashMap;

/**
 * Created by liao on 2018/3/24.
 */

public class HashMapFactory {

    private static HashMap<String, String> map;

    public static HashMap getHashMap() {
        if (map == null) {
            synchronized (HashMapFactory.class) {
                if (map == null)
                    map = new HashMap<>();
            }
        }
        map.clear();
        return map;
    }
}
