package com.epam.esm.module2boot.dao.jpaImpl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class HQLHelper {

    final static Map<String, String> nameTranslationMap =
            Map.of("gift_certificate.name", "gc.name",
                    "tag.name", "t.name",
                    "create_date","gc.createDate");
    final static Set<String> likeFields = Set.of("gc.name", "description");

    public static String getWhereStr(Map<String, Object> params) {
        if (params == null || params.isEmpty()) return "";

        return "WHERE " + params.keySet().stream()
                .map(String::toLowerCase)
                .map(s -> nameTranslationMap.getOrDefault(s, s))
                .map(o -> String.format(
                        likeFields.contains(o) ?
                                "%1$s like :%2$s" : "%1$s = :%2$s"
                        , o, translateParameter(o)
                ))
                .collect(Collectors.joining(" AND "));
    }

    public static String translateParameter(String param) {
        return 'p' + nameTranslationMap.getOrDefault(param, param).replace('.', '_');
    }

    public static String getSortingSubStr(List<String> sortingFieldsList) {
        String sortString = "";
        if (sortingFieldsList != null && sortingFieldsList.size() > 0) {

            List<String> translatedNames = sortingFieldsList.stream()
                    .map(HQLHelper::replaceSQLNamesToHQL)
                    .toList();
            sortString = " ORDER BY " + String.join(", ", translatedNames);
        }
        return sortString;
    }

    private static String replaceSQLNamesToHQL(String s) {
        for (Map.Entry<String, String> entry : nameTranslationMap.entrySet()) {
            s = s.replace(entry.getKey(), entry.getValue());
        }
        return s;
    }
}
