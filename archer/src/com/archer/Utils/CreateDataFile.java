package com.archer.Utils;

import java.awt.Color;
import java.awt.Font;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis3D;
import org.jfree.chart.axis.NumberAxis3D;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import com.archer.model.Answer;


public class CreateDataFile {
	//��һ��ÿһ�е�Ԫ������
	  public static final String[] RECORES_COLUMNS = new String[]{"���_#_3000","��������_#_7000" };
	  //Ҫ���Answer���е�id��st��Աֵ
	  public static final String[] RECORES_FIELDS = new String[]{"id","st"};
	  
	  
  public static InputStream createTextPOI(List<Answer> ans){
	  
      
      HSSFWorkbook workbook = new HSSFWorkbook();
      ExcelUtil<Answer> ansSheet = new ExcelUtil<Answer>();
      ByteArrayOutputStream out=new ByteArrayOutputStream();
      try {
			ansSheet.creatAuditSheet(workbook, "�ʾ��", ans, RECORES_COLUMNS, RECORES_FIELDS);
			out=new ByteArrayOutputStream();
			workbook.write(out); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      return new ByteArrayInputStream(out.toByteArray());
  }
  
  
  /**
   * 
   * @param map �����ݵļ���
   * @param quesId	map�𰸶�Ӧ���������
   * @param type	����ͳ��ͼ�ĸ�ʽ��Ĭ����״ͼ��1Ϊ��״ͼ��2ΪԲ��ͼ��
   */
  public static InputStream createChioceDataPic(Map<Integer,Object> map,int quesId,int type){
	 switch(type){
	 case 1: return createHistogram(map, quesId);
	 case 2: return createPie(map, quesId);
	 default: return createHistogram(map, quesId);
	 }
  }
  /**
   * ������״ͼ
   * @param map
   * @param quesId
   * @return
   */
  private static InputStream createHistogram(Map<Integer,Object> map,int quesId){
	  DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	  for(int key : map.keySet()){
		  char c=(char) ('A'+key-1);
         dataset.addValue((Integer)map.get(key), "ѡ��", String.valueOf(c));
	  }
		//����ģʽ
		JFreeChart chart = ChartFactory.createBarChart3D(
				quesId+"�������������״ͼ",		//ͼ�ε������� 
				"ѡ��ֵ",				//X����ı�ǩ���� 
				"����",						//Y����ı�ǩ���� 
				dataset, 					//ͼ�ε����ݼ���
				PlotOrientation.VERTICAL,	//ͼ�����ʾ��ʽ��ˮƽ/��ֱ�� 
				true,						//�Ƿ���ʾ�ӱ��� 
				true,						//�Ƿ���ͼ����������ʾ���� 
				true						//�Ƿ�������URL
		);
		//���������������
		chart.getTitle().setFont(new Font("�����п�", Font.BOLD, 50));
		//�����ӱ��������
		chart.getLegend().setItemFont(new Font("�����п�", Font.BOLD, 40));

		//�����ܵı���ɫ
		chart.setBackgroundPaint(Color.WHITE);
		//��ȡͼ���������
		/**
		 * ͼ���������
		 *  ��1��ʹ�öϵ㣺
		 *  ��2��ʹ��System.out.println(chart.getPlot())
		 *  ��3��ʹ��API�ĵ�
		 */
		CategoryPlot categoryPlot = (CategoryPlot) chart.getPlot();

		//����ͼ��ı���ɫ
		categoryPlot.setBackgroundPaint(Color.white);
		//��ȡX�����
		CategoryAxis3D categoryAxis3D = (CategoryAxis3D)categoryPlot.getDomainAxis();
		//��ȡY�����
		NumberAxis3D numberAxis3D = (NumberAxis3D) categoryPlot.getRangeAxis();
		//X���������
		categoryAxis3D.setLabelFont(new Font("�����п�", Font.BOLD, 30));
		//X���ϵ�����
		categoryAxis3D.setTickLabelFont(new Font("�����п�", Font.BOLD, 30));
		//Y���������
		numberAxis3D.setLabelFont(new Font("�����п�", Font.BOLD, 30));
		//Y���ϵ�����
		numberAxis3D.setTickLabelFont(new Font("�����п�", Font.BOLD, 30));
		//����Y���ϵĿ̶���1Ϊ��λ
		numberAxis3D.setAutoTickUnitSelection(false);
		NumberTickUnit unit = new NumberTickUnit(1);
		numberAxis3D.setTickUnit(unit);
		//��ȡ��ͼ�������
		BarRenderer3D barRenderer3D = (BarRenderer3D)categoryPlot.getRenderer();
		//��ͼ�α��������
		barRenderer3D.setMaximumBarWidth(0.08);
		//����ͼ������������
		barRenderer3D.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		barRenderer3D.setBaseItemLabelsVisible(true);
		barRenderer3D.setBaseItemLabelFont(new Font("�����п�", Font.BOLD, 30));
		ByteArrayOutputStream out =new ByteArrayOutputStream();
		try {
			ChartUtilities.writeChartAsJPEG(out, chart, 1000, 800);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ByteArrayInputStream(out.toByteArray());
  }
  
  
  
  /**
   * ����Բ��ͼ
   * @param map
   * @param quesId
 * @return 
   */
  private static InputStream createPie(Map<Integer,Object> map,int quesId){
		//ͼ�ε����ݼ���
		DefaultPieDataset dataset = new DefaultPieDataset(); 
		for(int key : map.keySet()){
			  char c=(char) ('A'+key-1);
		         dataset.setValue(String.valueOf(c),(Integer)map.get(key));
		}
		//����ģʽ
		JFreeChart chart = ChartFactory.createPieChart3D(
				quesId+"�����������Բ��ͼ",		//ͼ�ε������� 
				dataset, 					//ͼ�ε����ݼ���
				true,						//�Ƿ���ʾ�ӱ��� 
				true,						//�Ƿ���ͼ����������ʾ���� 
				true						//�Ƿ�������URL
			);
		//���������������
		chart.getTitle().setFont(new Font("�����п�", Font.BOLD, 50));
		//�����ӱ��������
		chart.getLegend().setItemFont(new Font("�����п�", Font.BOLD, 40));
		chart.setBackgroundPaint(Color.white);
		chart.setBorderVisible(false);
		//��ȡͼ���������
		PiePlot3D piePlot3D = (PiePlot3D) chart.getPlot();
		piePlot3D.setLabelFont(new Font("�����п�", Font.BOLD, 35));
		piePlot3D.setBackgroundPaint(Color.WHITE);
		//��ͼ������ʾ��ֵ����ʽ��A 12 ��60%��
		String labelFormat = "{0} {1} ({2})";
		piePlot3D.setLabelGenerator(new StandardPieSectionLabelGenerator(labelFormat));
		ByteArrayOutputStream out =new ByteArrayOutputStream();
		try {
			ChartUtilities.writeChartAsJPEG(out, chart, 1000, 800);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ByteArrayInputStream(out.toByteArray());
   }
  
}
