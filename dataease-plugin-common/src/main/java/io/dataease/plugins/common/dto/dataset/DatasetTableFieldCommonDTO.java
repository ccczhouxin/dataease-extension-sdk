package io.dataease.plugins.common.dto.dataset;

import io.dataease.plugins.common.base.domain.DatasetTableField;
import lombok.Data;

/**
 * Author: wangjiahao
 * Date: 2022/10/8
 * Description:
 */
@Data
public class DatasetTableFieldCommonDTO  extends DatasetTableField {
    private String jsonPath;
}
