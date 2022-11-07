package io.dataease.plugins.datasource.query;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.dataease.plugins.common.base.domain.DatasetTableField;
import io.dataease.plugins.common.dto.datasource.TableField;
import io.dataease.plugins.datasource.request.ApplicationDefinition;
import io.dataease.plugins.datasource.request.ApplicationDefinitionRequest;
import io.dataease.plugins.datasource.utils.HttpClientConfig;
import io.dataease.plugins.datasource.utils.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Author: wangjiahao
 * Date: 2022/9/22
 * Description:
 */
public abstract class ApplicationDockProvider implements IApplicationDockProvider{

    static public String execHttpRequest(ApplicationDefinition applicationDefinition, int socketTimeout) throws Exception {
        String response = "";
        HttpClientConfig httpClientConfig = new HttpClientConfig();
        httpClientConfig.setSocketTimeout(socketTimeout * 1000);
        ApplicationDefinitionRequest applicationDefinitionRequest = applicationDefinition.getRequest();
        for (Map header : applicationDefinitionRequest.getHeaders()) {
            if (header.get("name") != null && StringUtils.isNotEmpty(header.get("name").toString()) && header.get("value") != null && StringUtils.isNotEmpty(header.get("value").toString())) {
                httpClientConfig.addHeader(header.get("name").toString(), header.get("value").toString());
            }
        }

        if (applicationDefinitionRequest.getAuthManager() != null
                && StringUtils.isNotBlank(applicationDefinitionRequest.getAuthManager().getUsername())
                && StringUtils.isNotBlank(applicationDefinitionRequest.getAuthManager().getPassword())
                && applicationDefinitionRequest.getAuthManager().getVerification().equals("Basic Auth")) {
            String authValue = "Basic " + Base64.getUrlEncoder().encodeToString((applicationDefinitionRequest.getAuthManager().getUsername()
                    + ":" + applicationDefinitionRequest.getAuthManager().getPassword()).getBytes());
            httpClientConfig.addHeader("Authorization", authValue);
        }

        switch (applicationDefinition.getMethod()) {
            case "GET":
                response = HttpClientUtil.get(applicationDefinition.getUrl().trim(), httpClientConfig);
                break;
            case "POST":
                if (applicationDefinitionRequest.getBody().get("type") == null) {
                    throw new Exception("请求类型不能为空");
                }
                String type = applicationDefinitionRequest.getBody().get("type").toString();
                if (StringUtils.equalsAny(type, "JSON", "XML", "Raw")) {
                    String raw = null;
                    if (applicationDefinitionRequest.getBody().get("raw") != null) {
                        raw = applicationDefinitionRequest.getBody().get("raw").toString();
                        response = HttpClientUtil.post(applicationDefinition.getUrl(), raw, httpClientConfig);
                    }
                }
                if (StringUtils.equalsAny(type, "Form_Data", "WWW_FORM")) {
                    if (applicationDefinitionRequest.getBody().get("kvs") != null) {
                        Map<String, String> body = new HashMap<>();
                        JsonArray kvsArr = JsonParser.parseString(applicationDefinitionRequest.getBody().get("kvs").toString()).getAsJsonArray();
                        for (int i = 0; i < kvsArr.size(); i++) {
                            JsonObject kv = kvsArr.get(i).getAsJsonObject();
                            if (kv.get("name") != null) {
                                body.put(kv.get("name").getAsString(), kv.get("value").getAsString());
                            }
                        }
                        response = HttpClientUtil.post(applicationDefinition.getUrl(), body, httpClientConfig);
                    }
                }
                break;
            default:
                break;
        }
        return response;
    }

    public List<TableField> getTableFields(ApplicationDefinition definition) throws Exception {
        List<TableField> tableFields = new ArrayList<>();
        for (DatasetTableField field : definition.getFields()) {
            TableField tableField = new TableField();
            tableField.setFieldName(field.getName());
            tableField.setRemarks(field.getName());
            tableField.setFieldSize(field.getSize());
            tableField.setFieldType(field.getDeExtractType().toString());
            tableFields.add(tableField);
        }
        return tableFields;
    }

}
