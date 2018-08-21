package com.sun.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: <br/>
 * Date: 2018-08-21
 *
 * @author Sun
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelHeader {

    private int firstRow;
    private int lastRow;
    private int firstCol;
    private int lastCol;

    private String content;

    // sheet.addMergedRegion(new CellRangeAddress(0,0,0,1));

}
