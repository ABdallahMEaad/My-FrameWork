package Utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;

public class ExcelFileManger {

    private static final Logger log = LogManager.getLogger(ExcelFileManger.class);

    public XSSFWorkbook workbook;
    public XSSFSheet sheet;

    public ExcelFileManger(String filepath, String sheetname) {

        log.info("Loading Excel file: {}", filepath);
        log.info("Opening Excel sheet by name: {}", sheetname);

        try (FileInputStream fileInputStream = new FileInputStream(filepath)) {

            workbook = new XSSFWorkbook(fileInputStream);
            sheet = workbook.getSheet(sheetname);

            if (sheet == null) {
                log.warn("Excel sheet not found: {}", sheetname);
            } else {
                log.info("Excel sheet loaded successfully: {}", sheetname);
            }

        } catch (Exception e) {

            log.error(
                    "Failed to load Excel file: {} or sheet: {}",
                    filepath,
                    sheetname,
                    e
            );
        }
    }

    public ExcelFileManger(String filepath, int sheetIndex) {

        log.info("Loading Excel file: {}", filepath);
        log.info("Opening Excel sheet by index: {}", sheetIndex);

        try (FileInputStream fileInputStream = new FileInputStream(filepath)) {

            workbook = new XSSFWorkbook(fileInputStream);
            sheet = workbook.getSheetAt(sheetIndex);

            log.info("Excel sheet loaded successfully at index: {}", sheetIndex);

        } catch (Exception e) {

            log.error(
                    "Failed to load Excel file: {} or sheet index: {}",
                    filepath,
                    sheetIndex,
                    e
            );
        }
    }

    public int getRowsCount() {

        int rowsCount = sheet.getPhysicalNumberOfRows();

        log.debug("Excel rows count: {}", rowsCount);

        return rowsCount;
    }

    public int getColums() {

        int columnsCount = sheet.getRow(0).getPhysicalNumberOfCells();

        log.debug("Excel columns count: {}", columnsCount);

        return columnsCount;
    }

    public String getFormla(int rowindex, int collIndex) {

        log.debug(
                "Getting formula from Excel cell - Row: {} | Column: {}",
                rowindex,
                collIndex
        );

        Cell cell = sheet.getRow(rowindex).getCell(collIndex);

        String formula = cell.getCellFormula();

        log.debug("Formula retrieved: {}", formula);

        return formula;
    }

    public String getSpecificeCellValue(int rowindex, int collIndex) {

        log.debug(
                "Getting cell value - Row: {} | Column: {}",
                rowindex,
                collIndex
        );

        Cell cell = sheet.getRow(rowindex).getCell(collIndex);

        DataFormatter dataFormatter = new DataFormatter();

        String value = dataFormatter.formatCellValue(cell);

        log.debug("Cell value retrieved: {}", value);

        return value;
    }
}