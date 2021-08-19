package zgh;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

/**
 * excel
 * @author zhangguihua(hua7381@163.com)
 * date: 2018年4月8日
 */
public class Xls {
    
    /**
     * 写XSL
     * @throws Exception
     */
    @Test
    public void fn1() throws Exception {
        HSSFWorkbook workbook = null;
        try {
            workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("sheet1");
            
            for (int i=0; i<2; i++) {
                HSSFRow row = sheet.createRow(i);
                for (int j=0; j<3; j++) {
                    HSSFCell cell = row.createCell(j);
                    cell.setCellValue("hello,"+i+","+j);
                }
            }

            workbook.write(new FileOutputStream("C:/test//xsl1.xls"));
        } finally {
            if (workbook != null) workbook.close();
        }
    }
    
    /**
     * 读取XSL
     * @throws Exception
     */
    @Test
    public void fn2() throws Exception {
        Workbook wb = null;
        try {
            wb = new HSSFWorkbook(new FileInputStream(Tool.getClasspath()+"xsl2.xls"));
            // 获取sheet数目
            for (int t = 0; t < wb.getNumberOfSheets(); t++) {
                Sheet sheet = wb.getSheetAt(t);
                Row row = null;
                int lastRowNum = sheet.getLastRowNum();

                // 循环读取
                for (int i = 0; i <= lastRowNum; i++) {
                    row = sheet.getRow(i);
                    if (row != null) {
                        // 获取每一列的值
                        for (int j = 0; j < row.getLastCellNum(); j++) {
                            Cell cell = row.getCell(j);
                            Object value = getCellValue(cell);
                            System.out.print(value + "," + (value.getClass().toString().substring(value.getClass().toString().lastIndexOf(".")+1)) + " | ");
                        }
                        System.out.println();
                    }
                }
            }
        } finally {
            wb.close();
        }
    }
    
    @SuppressWarnings("deprecation")
    private Object getCellValue(Cell cell) {
        Object result = "";
        if (cell != null) {
            switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                result = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                result = cell.getNumericCellValue();
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                result = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_FORMULA:
                result = cell.getCellFormula();
                break;
            case Cell.CELL_TYPE_ERROR:
                result = cell.getErrorCellValue();
                break;
            case Cell.CELL_TYPE_BLANK:
                break;
            default:
                break;
            }
        }
        return result;
    }
    
}
