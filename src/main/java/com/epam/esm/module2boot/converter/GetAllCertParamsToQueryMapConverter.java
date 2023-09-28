package com.epam.esm.module2boot.converter;

import org.springframework.data.domain.Sort;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
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
                    "substr",GetAllCertParamsToQueryMapConverter::getString,
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
                final Function<List<String>, Object> converter=paramConverters.get(keyLowerCase);
                if (converter!=null)
                    convertedMap.put(keyLowerCase, converter.apply(value));
            }
        });
        return convertedMap;
    }

    public Sort getSort() {


        List<String> strOrderList = inParams.get("sort");
        if (strOrderList == null || strOrderList.isEmpty()) return Sort.unsorted();

        final String[] allowedSortFields= {"createDate","description","name","price"};


        return Sort.by(strOrderList.stream()
                        .map(param -> param.split(","))
                        .filter(paramStrings -> Arrays.binarySearch(allowedSortFields,paramStrings[0])>=0)
                        .map(paramStrings -> {
                            Sort.Direction direction =
                                    paramStrings.length >1 && "desc".equalsIgnoreCase(paramStrings[1]) ?
                                    Sort.Direction.DESC : Sort.Direction.ASC;
                            return Sort.Order.by(paramStrings[0]).with(direction);
                        })
                        .collect(Collectors.toList())
        );

    }
}
