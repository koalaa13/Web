package ru.itmo.wp.utils;

import java.util.ArrayList;
import java.util.List;

public class TagHelper {
    public static List<String> parseTags(String tags) {
        String[] tagsArray = tags.split("\\s+");
        List<String> res = new ArrayList<>();
        for (String tag : tagsArray) {
            if (!tag.isEmpty()) {
                res.add(tag);
            }
        }
        return res;
    }
}
