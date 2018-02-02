package com.achilles.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.achilles.dao.RankingDAO;
import com.achilles.dao.ScoreDAO;
import com.achilles.dao.impl.RankingDAOImpl;
import com.achilles.dao.impl.ScoreDAOImpl;
import com.achilles.model.Battle;
import com.achilles.model.Ranking;
import com.achilles.model.Score;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ImportBattleResult {
	private int count = 0;
	public void importBattleResult() throws Exception {
		File inData = new File("E:\\work\\starcraft\\workspace\\achilles\\doc\\battle.xlsx");
		InputStream in=new FileInputStream(inData);
        Workbook workbook = WorkbookFactory.create(in);
        
        int sheetCount = workbook.getNumberOfSheets();  //Sheet的数量  
        Integer[] roundIds = {2, 3};
        int index = 0;
        for (int s = 0; s < sheetCount; s++) {
        	Sheet sheet = workbook.getSheetAt(s);
            String sheetName = sheet.getSheetName();
            if(sheetName.equals("第一周") || sheetName.equals("第二周")) {
            	parseRecord(sheet, roundIds[index++]);
            }
        }
        
        in.close();
	}

	private void parseRecord(Sheet sheet, int round_id) throws Exception {
		int rowCount = sheet.getPhysicalNumberOfRows(); //获取总行数
		Map<String, Integer> idx = new HashMap<String, Integer>();

		ScoreDAO sdao = new ScoreDAOImpl();
		List<Score> scores = sdao.GetScoreByLastRoundRanking(round_id);
		Map<Integer, Integer> rankingMap = new HashMap<Integer, Integer>();
		for(int i = 0; i< scores.size(); i++) {
			Score score = scores.get(i);
			rankingMap.put(score.getPlayerId(), i+1);
		}
		
		String challengerName = "";
		String challengerLoginId = "";
		String challengerId = "";
		String challengerRace = "";
		String result = "";
		String adversaryName = "";
		String adversaryLoginId = "";
		String adversaryId = "";
		String adversaryRace = "";
		String plat = "";
		String vod = "";
		String timestamp = "";
		String memo = "";
		//遍历每一行  
        for (int r = 0; r < rowCount; r++) {
        	
        	if(r == 22) {
        		int a = 1;
        	}
        	challengerName = "";
    		challengerLoginId = "";
    		challengerId = "";
    		challengerRace = "";
    		result = "";
    		adversaryName = "";
    		adversaryLoginId = "";
    		adversaryId = "";
    		adversaryRace = "";
    		plat = "";
    		vod = "";
    		timestamp = "";
    		memo = "";
        	Row row = sheet.getRow(r);
        	if(row == null) {
        		continue;
        	}
        	
        	int cellCount = row.getLastCellNum(); //获取总列数 
        	//遍历每一列  
            for (int c = 0; c < cellCount; c++) {
            	Cell cell = row.getCell(c);
            	String cellValue = getCellValue(cell);

            	if(r == 2) {
            		if ( "挑战者名称".equals(cellValue) ) {
            			idx.put("挑战者名称", c);
            		} else if ( "挑战者账号".equals(cellValue) ) {
            			idx.put("挑战者账号", c);
            		} else if ( "挑战者ID".equals(cellValue) ) {
            			idx.put("挑战者ID", c);
            		} else if ( "挑战者本场种族".equals(cellValue) ) {
            			idx.put("挑战者本场种族", c);
            		} else if ( "输赢关系".equals(cellValue) ) {
            			idx.put("输赢关系", c);
            		} else if ( "守擂者名称".equals(cellValue) ) {
            			idx.put("守擂者名称", c);
            		} else if ( "守雷者账号".equals(cellValue) ) {
            			idx.put("守雷者账号", c);
            		} else if ( "守擂者ID".equals(cellValue) ) {
            			idx.put("守擂者ID", c);
            		}
            		else if ( "守擂者本场种族".equals(cellValue) ) {
            			idx.put("守擂者本场种族", c);
            		}     
            		else if ( "比赛地图".equals(cellValue) ) {
            			idx.put("比赛地图", c);
            		}     
            		else if ( "vod/rep文件名".equals(cellValue) ) {
            			idx.put("vod/rep文件名", c);
            		}     
            		else if ( "比赛时间".equals(cellValue) ) {
            			idx.put("比赛时间", c);
            		}     
            		else if ( "本场描述".equals(cellValue) ) {
            			idx.put("本场描述", c);
            		}     
            	} else if(r>2){
            		if(idx.size() == 0) {
            			throw new Exception("导入数据文件格式不正确!");
            		}
            		if( c == idx.get("挑战者名称") ) {
            			if( cellValue != null && cellValue.length() > 0 ) {
            				challengerLoginId = cellValue;
            			}
            		} else if ( c == idx.get("挑战者账号") ) {
            			if( cellValue != null && cellValue.length() > 0 ) {
            				challengerName = cellValue;
            			}
            		} else if ( c== idx.get("挑战者ID") ) {
            			if( cellValue != null && cellValue.length() > 0 ) {
            				challengerId = cellValue;
            			}
            		} else if ( c== idx.get("挑战者本场种族") ) {
            			if( cellValue != null && cellValue.length() > 0 ) {
            				challengerRace = cellValue;
            			}
            		} else if ( c== idx.get("输赢关系") ) {
            			if( cellValue != null && cellValue.length() > 0 ) {
            				result = cellValue;
            			}
            		} else if ( c== idx.get("守擂者名称") ) {
            			if( cellValue != null && cellValue.length() > 0 ) {
            				adversaryLoginId = cellValue;
            			}
            		} else if ( c== idx.get("守雷者账号") ) {
            			if( cellValue != null && cellValue.length() > 0 ) {
            				adversaryName = cellValue;
            			}
            		} else if ( c== idx.get("守擂者ID") ) {
            			if( cellValue != null && cellValue.length() > 0 ) {
            				adversaryId = cellValue;
            			}
            		} else if ( c== idx.get("守擂者本场种族") ) {
            			if( cellValue != null && cellValue.length() > 0 ) {
            				adversaryRace = cellValue;
            			}
            		} else if ( c== idx.get("比赛地图") ) {
            			if( cellValue != null && cellValue.length() > 0 ) {
            				plat = cellValue;
            			}
            		} else if ( c== idx.get("vod/rep文件名") ) {
            			if( cellValue != null && cellValue.length() > 0 ) {
            				vod = cellValue;
            			}
            		} else if ( c== idx.get("比赛时间") ) {
            			if( cellValue != null && cellValue.length() > 0 ) {
            				timestamp = cellValue;
            			}
            		} else if ( c== idx.get("本场描述") ) {
            			if( cellValue != null && cellValue.length() > 0 ) {
            				memo = cellValue;
            			}
            		}
            	}
            }
            
            if( r > 2 ) {
            	count++;
            	String countId = "" + count;
            	int challenger_rank = rankingMap.get(Integer.parseInt(challengerId));
            	int adversary_rank = rankingMap.get(Integer.parseInt(adversaryId));
            	String sqlStr = "INSERT INTO battle (challenger_id, challenger_name, challenger_login_id, challenger_race, challenger_rank, result, adversary_id, adversary_name, adversary_login_id, adversary_race, adversary_rank, map_id, map_name, vod, round_id, timestamp, memo ) ";
            	sqlStr += "VALUES (%s, '%s', '%s', '%s', %d, %s, %s, '%s', '%s', '%s', %d, %s, '%s', '%s', %d, '%s', '%s');";
            	
            	if( "挑战者胜".equals(result) ) {
            		result = "1";
            	}
            	else if ( "守擂者胜".equals(result) ) {
            		result = "2";
            	}
            	else if ( "平局".equals(result) ) {
            		result = "3";
            	}
            	else if ( "挑战者缺席".equals(result) ) {
            		result = "4";
            		continue;
            	}
            	else if ( "守擂者缺席".equals(result) ) {
            		result = "5";
            		continue;
            	}
            	String platId = null;
            	String platName = null;
            	if( plat.trim().length() == 0 ) {
            		platId = "";
            		platName = "";
            	}
            	else {
            		platId = plat.substring(0, plat.indexOf(" ")).substring(1);
            		platName = plat.substring(plat.indexOf(" ")+1);
            	}
            	System.out.printf(sqlStr , challengerId, challengerName, challengerLoginId, challengerRace, challenger_rank, result, adversaryId, adversaryName, adversaryLoginId, adversaryRace, adversary_rank, platId, platName, vod, round_id, timestamp, memo );
            	System.out.println();
            }
        }
        return;
	}
	
	private String getCellValue(Cell cell) {
		if(cell == null) {
			return "";
		}
		int cellType = cell.getCellType();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String cellValue = null;  
        switch(cellType) {
            case Cell.CELL_TYPE_STRING: //文本  
                cellValue = cell.getStringCellValue();  
                break;  
            case Cell.CELL_TYPE_NUMERIC: //数字、日期  
                if(DateUtil.isCellDateFormatted(cell)) {  
                    cellValue = sdf.format(cell.getDateCellValue()); //日期型  
                }  
                else {  
                    cellValue = String.valueOf(cell.getNumericCellValue()); //数字  
                    if(cellValue.contains(".")) {
                    	cellValue = cellValue.substring(0, cellValue.indexOf('.'));
                    }
                }  
                break;  
            case Cell.CELL_TYPE_BOOLEAN: //布尔型  
                cellValue = String.valueOf(cell.getBooleanCellValue());  
                break;  
            case Cell.CELL_TYPE_BLANK: //空白  
                cellValue = cell.getStringCellValue();  
                break;  
            case Cell.CELL_TYPE_ERROR: //错误  
                cellValue = "错误";  
                break;  
            case Cell.CELL_TYPE_FORMULA: //公式  
                cellValue = "错误";  
                break;  
            default:  
                cellValue = "错误";  
        }
        cellValue = cellValue.trim();
        return cellValue;
	}
	
	public static void main(String[] args) {
		try{
			ImportBattleResult test = new ImportBattleResult();
			test.importBattleResult();
			System.out.println("finish.");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return;
	}
}
