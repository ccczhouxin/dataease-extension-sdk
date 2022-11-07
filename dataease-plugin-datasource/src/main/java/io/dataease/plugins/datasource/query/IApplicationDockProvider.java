package io.dataease.plugins.datasource.query;

import io.dataease.plugins.common.base.domain.DatasetTableField;
import io.dataease.plugins.common.dto.datasource.TableDesc;
import io.dataease.plugins.common.dto.datasource.TableField;
import io.dataease.plugins.common.request.datasource.DatasourceRequest;
import io.dataease.plugins.datasource.request.ApplicationDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: wangjiahao
 * Date: 2022/9/22
 * Description:
 */
public interface IApplicationDockProvider {
    abstract List<TableField> getTableFields(ApplicationDefinition definition) throws Exception;

    abstract List<String[]> getData(DatasourceRequest datasourceRequest) throws Exception;

    abstract List<TableDesc> getTables(DatasourceRequest datasourceRequest) throws Exception;

    abstract List<TableField> fetchResultField(DatasourceRequest datasourceRequest) throws Exception;

    abstract Map<String, List> fetchResultAndField(DatasourceRequest datasourceRequest) throws Exception;

    abstract List<TableField> getTableFields(DatasourceRequest datasourceRequest) throws Exception;

    abstract String checkStatus(DatasourceRequest datasourceRequest) throws Exception;
}
