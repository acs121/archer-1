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
	//第一行每一列单元格名称
	  public static final String[] RECORES_COLUMNS = new String[]{"序号_#_3000","答题内容_#_7000" };
	  //要输出Answer类中的id和st成员值
	  public static final String[] RECORES_FIELDS = new String[]{"id","st"};
	  
	  
  public static InputStream createTextPOI(List<Answer> ans){
	  
      
      HSSFWorkbook workbook = new HSSFWorkbook();
      ExcelUtil<Answer> ansSheet = new ExcelUtil<Answer>();
      ByteArrayOutputStream out=new ByteArrayOutputStream();
      try {
			ansSheet.creatAuditSheet(workbook, "问卷答案", ans, RECORES_COLUMNS, RECORES_FIELDS);
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
   * @param map 答案数据的集合
   * @param quesId	map答案对应的问题题号
   * @param type	生成统计图的格式（默认柱状图，1为柱状图，2为圆饼图）
   */
  public static InputStream createChioceDataPic(Map<Integer,Object> map,int quesId,int type){
	 switch(type){
	 case 1: return createHistogram(map, quesId);
	 case 2: return createPie(map, quesId);
	 default: return createHistogram(map, quesId);
	 }
  }
  /**
   * 生成柱状图
   * @param map
   * @param quesId
   * @return
   */
  private static InputStream createHistogram(Map<Integer,Object> map,int quesId){
	  DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	  for(int key : map.keySet()){
		  char c=(char) ('A'+key-1);
         dataset.addValue((Integer)map.get(key), "选项", String.valueOf(c));
	  }
		//工厂模式
		JFreeChart chart = ChartFactory.createBarChart3D(
				quesId+"题答题结果数据柱状图",		//图形的主标题 
				"选项值",				//X轴外的标签名称 
				"数量",						//Y轴外的标签名称 
				dataset, 					//图形的数据集合
				PlotOrientation.VERTICAL,	//图表的显示形式（水平/垂直） 
				true,						//是否显示子标题 
				true,						//是否在图形上生成提示工具 
				true						//是否点击生成URL
		);
		//处理主标题的乱码
		chart.getTitle().setFont(new Font("华文行楷", Font.BOLD, 50));
		//处理子标题的乱码
		chart.getLegend().setItemFont(new Font("华文行楷", Font.BOLD, 40));

		//设置总的背景色
		chart.setBackgroundPaint(Color.WHITE);
		//获取图表区域对象
		/**
		 * 图表区域对象：
		 *  （1）使用断点：
		 *  （2）使用System.out.println(chart.getPlot())
		 *  （3）使用API文档
		 */
		CategoryPlot categoryPlot = (CategoryPlot) chart.getPlot();

		//设置图表的背景色
		categoryPlot.setBackgroundPaint(Color.white);
		//获取X轴对象
		CategoryAxis3D categoryAxis3D = (CategoryAxis3D)categoryPlot.getDomainAxis();
		//获取Y轴对象
		NumberAxis3D numberAxis3D = (NumberAxis3D) categoryPlot.getRangeAxis();
		//X轴外的乱码
		categoryAxis3D.setLabelFont(new Font("华文行楷", Font.BOLD, 30));
		//X轴上的乱码
		categoryAxis3D.setTickLabelFont(new Font("华文行楷", Font.BOLD, 30));
		//Y轴外的乱码
		numberAxis3D.setLabelFont(new Font("华文行楷", Font.BOLD, 30));
		//Y轴上的乱码
		numberAxis3D.setTickLabelFont(new Font("华文行楷", Font.BOLD, 30));
		//设置Y轴上的刻度以1为单位
		numberAxis3D.setAutoTickUnitSelection(false);
		NumberTickUnit unit = new NumberTickUnit(1);
		numberAxis3D.setTickUnit(unit);
		//获取绘图区域对象
		BarRenderer3D barRenderer3D = (BarRenderer3D)categoryPlot.getRenderer();
		//让图形变得苗条点
		barRenderer3D.setMaximumBarWidth(0.08);
		//让在图形上生成数字
		barRenderer3D.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		barRenderer3D.setBaseItemLabelsVisible(true);
		barRenderer3D.setBaseItemLabelFont(new Font("华文行楷", Font.BOLD, 30));
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
   * 生成圆饼图
   * @param map
   * @param quesId
 * @return 
   */
  private static InputStream createPie(Map<Integer,Object> map,int quesId){
		//图形的数据集合
		DefaultPieDataset dataset = new DefaultPieDataset(); 
		for(int key : map.keySet()){
			  char c=(char) ('A'+key-1);
		         dataset.setValue(String.valueOf(c),(Integer)map.get(key));
		}
		//工厂模式
		JFreeChart chart = ChartFactory.createPieChart3D(
				quesId+"题答题结果数据圆饼图",		//图形的主标题 
				dataset, 					//图形的数据集合
				true,						//是否显示子标题 
				true,						//是否在图形上生成提示工具 
				true						//是否点击生成URL
			);
		//处理主标题的乱码
		chart.getTitle().setFont(new Font("华文行楷", Font.BOLD, 50));
		//处理子标题的乱码
		chart.getLegend().setItemFont(new Font("华文行楷", Font.BOLD, 40));
		chart.setBackgroundPaint(Color.white);
		chart.setBorderVisible(false);
		//获取图表区域对象
		PiePlot3D piePlot3D = (PiePlot3D) chart.getPlot();
		piePlot3D.setLabelFont(new Font("华文行楷", Font.BOLD, 35));
		piePlot3D.setBackgroundPaint(Color.WHITE);
		//在图形上显示数值：格式：A 12 （60%）
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
