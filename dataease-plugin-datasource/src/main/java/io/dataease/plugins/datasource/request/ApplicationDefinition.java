package io.dataease.plugins.datasource.request;

import cn.hutool.json.JSONObject;
import io.dataease.plugins.common.base.domain.DatasetTableField;
import io.dataease.plugins.common.dto.dataset.DatasetTableFieldCommonDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class ApplicationDefinition {
    private String name;
    private String desc;
    private String url;
    private String method = "GET";
    private List<DatasetTableFieldCommonDTO> fields;
    private ApplicationDefinitionRequest request;
    private String dataPath;
    private String status;
    private List<Map<String,String>> datas = new ArrayList<>();
    private List<JSONObject> jsonFields = new ArrayList<>();
    private int previewNum = 10;
    private int maxPreviewNum = 10;
    private int serialNumber;

}
