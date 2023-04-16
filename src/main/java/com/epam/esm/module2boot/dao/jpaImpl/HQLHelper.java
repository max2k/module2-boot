package com.epam.esm.module2boot.dao.jpaImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class HQLHelper {

    final static Map<String, String> sql2hqlNameTranslationMap =
            Map.of("gift_certificate.name", "gc.name",
                    "tag.name", "t.name",
                    "description", "gc.description",
                    "^name","gc.name",
                    "price","gc.price",
                    "duration","gc.duration",
                    "create_date","gc.createDate",
                    "last_update_date","gc.lastUpdateDate"
                    );
    final static Set<String> likeFields = Set.of("gc.name", "gc.description");

    public static String getWhereStr(Map<String, Object> params) {
        if (params == null || params.isEmpty()) return "";

        return "WHERE " + params.keySet().stream()
                .map(String::toLowerCase)
                .map(s -> sql2hqlNameTranslationMap.getOrDefault(s, s))
                .map(o -> String.format(
                        likeFields.contains(o) ?
                                "%1$s like :%2$s" : "%1$s = :%2$s"
                        , o, translateParameter(o)
                ))
                .collect(Collectors.joining(" AND "));
    }

    public static String translateParameter(String param) {
        return 'p' + sql2hqlNameTranslationMap.getOrDefault(param, param).replace('.', '_');
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
        for (Map.Entry<String, String> entry : sql2hqlNameTranslationMap.entrySet()) {
            s = s.replaceAll(entry.getKey(), entry.getValue());
        }
        return s;
    }


    public static Map<String, Object> changeSQlNamesToHQL(Map<String, Object> fieldsToUpdate) {
        if (fieldsToUpdate == null || fieldsToUpdate.isEmpty() ) return fieldsToUpdate;


        HashMap<String,Object> hqlParams=new HashMap<>();

        for (Map.Entry<String, Object> entry:fieldsToUpdate.entrySet()){
            hqlParams.put(
                    sql2hqlNameTranslationMap.getOrDefault(entry.getKey(),entry.getKey())
                    ,entry.getValue());
        }
        return hqlParams;

    }

    public static String getSetStr(Map<String, Object> fieldSet) {
        List<String> paramList= fieldSet.keySet().stream()
                .map(s -> String.format("%1$s = :%2$s",s , s.replace(".","_")))
                .toList();

        return String.join(", ", paramList);
    }
}
