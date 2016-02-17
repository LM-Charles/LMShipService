package com.longmendelivery.service.model.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by rabiddesire on 04/06/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DimensionModel {
    private Integer length;
    private Integer width;
    private Integer height;
    private Integer weight;
}
