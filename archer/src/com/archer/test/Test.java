package com.archer.test;

import java.io.FileOutputStream;
import java.io.InputStream;

import com.archer.Utils.IOUtils;
import com.archer.dao.DataDao;

public class Test {
    public static final String[] RECORES_COLUMNS = new String[]{//�������ƣ���һ�е�ÿһ�е�Ԫ�����ƣ�
            "���_#_3000",
            "����_#_7000"           
            };
    /*the column will display on xls files. must the same as the entity fields.��Ӧ������ֶ�.*/
    public static final String[] RECORES_FIELDS = new String[]{//Ҫ������еĳ�Աֵ
        "id","st"
    };
	public static void main(String[] args) throws Exception{
//		List<Answer> as;
//		as=AnswerDao.getAnswerList("joker4", 3);
//		System.out.println(as);
//		 HSSFWorkbook workbook = new HSSFWorkbook();
//	        ExcelUtil<Answer> asSheet = new ExcelUtil<Answer>();
//	        asSheet.creatAuditSheet(workbook, "�����", 
//	                as, RECORES_COLUMNS, RECORES_FIELDS);
//	        
//	        FileOutputStream fileOut = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\t.xls"); 
//	        workbook.write(fileOut); 
//	        fileOut.close(); 
//	        Map<Integer, Object> m=new HashMap<Integer,Object>();
//	        m.put(1, 30);
//	        m.put(2, 40);
//	        m.put(3, 50);
//	        
//	        InputStream in=CreateDataFile.createChioceDataPic(m, 1, 2);
//	        FileOutputStream out = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\1.png");
//	        IOUtils.InStreamToOutStream(in, out);
//	        out.close();
//	        in.close();
//		System.out.println(QuestionnaireDao.getQuestionType("joker4", 2));
		DataDao d=new DataDao("jokr","joker4", 1);
		InputStream in=d.getData();
		System.out.println(d.type);
		FileOutputStream out = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\t.png");
		IOUtils.InStreamToOutStream(in, out);
		out.close();
		in.close();
	}

}
