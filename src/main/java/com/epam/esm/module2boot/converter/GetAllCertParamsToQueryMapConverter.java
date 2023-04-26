package com.epam.esm.module2boot.converter;

import org.springframework.data.domain.Sort;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class GetAllCertParamsToQueryMapConverter {

    private final MultiValueMap<String, String> inParams;

    private final Map<String, Function<List<String>, Object>> paramConverters =
            Map.of("name", GetAllCertParamsToQueryMapConverter::getString,
                    "description", GetAllCertParamsToQueryMapConverter::getString,
                    "tags", GetAllCertParamsToQueryMapConverter::getStringList);

    public GetAllCertParamsToQueryMapConverter(MultiValueMap<String, String> params) {
        this.inParams = params;
    }

    private static Object getString(List<String> list) {
        return list.get(0);
    }

    private static Object getStringList(List<String> list) {
        return list;
    }

    public Map<String, Object> getGiftCertQueryMap() {
        Map<String, Object> convertedMap = new HashMap<>();

        inParams.forEach((key, value) -> {
            String keyLowerCase = key.toLowerCase();
            if (value != null && !value.isEmpty()) {
                convertedMap.put(keyLowerCase, paramConverters.get(keyLowerCase).apply(value));
            }
        });
        return convertedMap;
    }

    public Sort getSort() {

        List<String> strOrderList = inParams.get("sort");
        if (strOrderList == null || strOrderList.isEmpty()) return Sort.unsorted();

        return Sort.by(strOrderList.stream()
                .map(s -> {
                    String[] sortParams = s.split(",");
                    Sort.Direction direction = Sort.Direction.ASC;
                    if (sortParams.length > 1) {
                        direction = Sort.Direction.fromString(sortParams[1]);
                    }
                    return Sort.Order.by(sortParams[0]).with(direction);
                })
                .collect(Collectors.toList())
        );

    }
}
