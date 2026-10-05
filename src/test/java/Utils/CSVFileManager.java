package Utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CSVFileManager {

    private static final Logger log = LogManager.getLogger(CSVFileManager.class);

    private FileReader reader;
    private List<String[]> rows;
    private Map<String, List<String>> columnWithRows;
    private CSVParser records;
    private String csvFilePath;
    private FileReader rowReader;
    private Iterable<CSVRecord> rowRecords;

    public CSVFileManager(String csvFilePath) {

        initializeVariables();

        this.csvFilePath = csvFilePath;

        try {

            reader = new FileReader(csvFilePath);

            records = CSVFormat.DEFAULT
                    .builder()
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .build()
                    .parse(reader);

            rowReader = new FileReader(csvFilePath);

            rowRecords = CSVFormat.DEFAULT
                    .builder()
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .build()
                    .parse(rowReader);

            log.info("Reading test data from [{}]", csvFilePath);

        } catch (IOException e) {

            log.error("Couldn't find the desired file [{}]", csvFilePath);
            e.printStackTrace();
        }
    }

    public List<String[]> getRows() {

        rows = new ArrayList<>();

        try (FileReader fileReader = new FileReader(csvFilePath);
             CSVParser parser = CSVFormat.DEFAULT
                     .builder()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .build()
                     .parse(fileReader)) {

            for (CSVRecord record : parser) {

                String[] row = new String[record.size()];

                for (int i = 0; i < record.size(); i++) {

                    row[i] = record.get(i);
                }

                rows.add(row);
            }

            log.info(
                    "Successfully retrieved all rows from [{}]",
                    csvFilePath
            );

        } catch (Exception e) {

            log.error(
                    "Error while retrieving rows: {}",
                    e.getMessage()
            );
        }

        return rows;
    }

    public List<String> getColumns() {

        try {

            List<String> columns =
                    new ArrayList<>(records.getHeaderNames());

            log.info("Successfully retrieved columns from [{}]", csvFilePath);

            return columns;

        } catch (Exception e) {

            log.error("Error while retrieving columns: {}", e.getMessage());

            return Collections.emptyList();
        }
    }

    public Map<String, List<String>> getColumnsWithData() {

        columnWithRows = new LinkedHashMap<>();

        try {

            List<String[]> rows = getRows();
            List<String> columns = getColumns();

            for (int i = 0; i < columns.size(); i++) {

                List<String> columnData = new ArrayList<>();

                for (String[] row : rows) {

                    if (i < row.length) {

                        columnData.add(row[i]);
                    }
                }

                columnWithRows.put(columns.get(i), columnData);
            }

            log.info(
                    "Data mapped successfully: {}",
                    columnWithRows
            );

        } catch (Exception e) {

            log.error(
                    "Error while mapping columns with data: {}",
                    e.getMessage()
            );
        }

        return columnWithRows;
    }

    public String getFirstColumn() {

        try {

            List<String> columns = getColumns();

            return columns.getFirst();

        } catch (Exception e) {

            log.error(
                    "Error while retrieving first column: {}",
                    e.getMessage()
            );

            return null;
        }
    }

    public String getLastColumn() {

        try {

            List<String> columns = getColumns();

            return columns.getLast();

        } catch (Exception e) {

            log.error(
                    "Error while retrieving last column: {}",
                    e.getMessage()
            );

            return null;
        }
    }

    public String getSpecificColumnName(int columnNumber) {

        try {

            return getColumns().get(columnNumber - 1);

        } catch (Exception e) {

            log.error(
                    "Error while retrieving column {}: {}",
                    columnNumber,
                    e.getMessage()
            );

            return null;
        }
    }

    public List<String> getSpecificColumnData(String columnName) {

        try {

            return getColumnsWithData().get(columnName);

        } catch (Exception e) {

            log.error(
                    "Error while retrieving column data [{}]: {}",
                    columnName,
                    e.getMessage()
            );

            return Collections.emptyList();
        }
    }

    public List<String> getSpecificColumnData(int columnNumber) {

        try {

            String columnName =
                    getSpecificColumnName(columnNumber);

            return getColumnsWithData().get(columnName);

        } catch (Exception e) {

            log.error(
                    "Error while retrieving column data: {}",
                    e.getMessage()
            );

            return Collections.emptyList();
        }
    }

    public String getCellData(int rowNumber, String columnName) {

        try {

            String[] row = getRows().get(rowNumber);

            int columnIndex =
                    getColumns().indexOf(columnName);

            return row[columnIndex];

        } catch (Exception e) {

            log.error(
                    "Error while retrieving cell data: {}",
                    e.getMessage()
            );

            return null;
        }
    }

    public String getCellData(int rowNumber, int columnNumber) {

        try {

            String[] row = getRows().get(rowNumber);

            return row[columnNumber - 1];

        } catch (Exception e) {

            log.error(
                    "Error while retrieving cell data: {}",
                    e.getMessage()
            );

            return null;
        }
    }

    public int getCellCount(String columnName) {

        try {

            return getColumnsWithData()
                    .get(columnName)
                    .size();

        } catch (Exception e) {

            log.error(
                    "Error while calculating cell count: {}",
                    e.getMessage()
            );

            return 0;
        }
    }

    public int getCellCount(int columnNumber) {

        try {

            String columnName =
                    getSpecificColumnName(columnNumber);

            return getColumnsWithData()
                    .get(columnName)
                    .size();

        } catch (Exception e) {

            log.error(
                    "Error while calculating cell count: {}",
                    e.getMessage()
            );

            return 0;
        }
    }

    public Map<String, Integer> getMostFrequentValue(String columnName) {

        List<String> columnData =
                getSpecificColumnData(columnName);

        Map<String, Integer> frequency =
                new HashMap<>();

        for (String value : columnData) {

            frequency.put(
                    value,
                    frequency.getOrDefault(value, 0) + 1
            );
        }

        if (frequency.isEmpty()) {

            return new LinkedHashMap<>();
        }

        int maxFrequency =
                Collections.max(frequency.values());

        Map<String, Integer> result =
                new LinkedHashMap<>();

        for (Map.Entry<String, Integer> entry :
                frequency.entrySet()) {

            if (entry.getValue() == maxFrequency) {

                result.put(
                        entry.getKey(),
                        entry.getValue()
                );
            }
        }

        log.info(
                "Most frequent value in [{}]: {}",
                columnName,
                result
        );

        return result;
    }

    private void initializeVariables() {

        reader = null;
        rowReader = null;
        records = null;
        rowRecords = null;
        rows = null;
        columnWithRows = null;
        csvFilePath = "";
    }
}