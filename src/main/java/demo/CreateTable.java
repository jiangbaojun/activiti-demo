package demo;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

public class CreateTable {

	public static void main(String[] args) {
		createTable();
	}
	
	/**
	 * 	创建表
	 * @Author		姜宝俊
	 * @Group 		技术组
	 * @Worker	 	1861 
	 * @date 		2017年12月22日 下午2:35:47     
	 * @Company		Vstsoft
	 * @version 	V1.0
	 *  Activiti工作流引擎的数据库表中的表名称都是以 ACT_.第二部分两个字母表示表的类型。使用模糊匹配的方式说明表的类型匹配activiti的服务API.
	 *     ACT_RE_*: RE代表仓储(Repository).这种表前缀以“static”表示流程定义信息或者流程资源信息（如流程的图表和规则等）.
	 *         ACT_RU_*: RU标识为运行(Runtime)时表。包含流程实例，用户任务和变量任务等在运行时的数据信息。这些表只存储Activiti在流程实例运行执行的数据，在流程结束的时候从表中去除数据。从而保持运行时候数据的表的快速和小数据量.
	 *         ACT_ID_*:ID标识为唯一(Identity)的。包含一些唯一的信息如用户，用户做等信息。
	 *         ACT_HI_*:HI表示历史数据(History)表，包括过期的流程实例，过期的变量和过期的任务等。
	 *         ACT_GE_*:GE表示公用(General data)的数据库表类型。
	 */
	@Test
	public static void createTable() {
	    // 工作流引擎的全部配置
	    ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration
	            .createStandaloneProcessEngineConfiguration();

	    // 链接数据的配置
	    processEngineConfiguration.setJdbcDriver("com.mysql.jdbc.Driver");
	    processEngineConfiguration
	            .setJdbcUrl("jdbc:mysql://localhost:3306/activiti6?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf8");
	    processEngineConfiguration.setJdbcUsername("root");
	    processEngineConfiguration.setJdbcPassword("123456");
	 
	    /*
	     * public static final String DB_SCHEMA_UPDATE_FALSE = "false";
	     * 不能自动创建表，需要表存在 public static final String DB_SCHEMA_UPDATE_CREATE_DROP
	     * = "create-drop"; 先删除表再创建表 public static final String
	     * DB_SCHEMA_UPDATE_TRUE = "true";如果表不存在，自动创建表
	     */
	    //如果表不存在，自动创建表
	    processEngineConfiguration
	            .setDatabaseSchemaUpdate(processEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
	    // 工作流的核心对象，ProcessEnginee对象
	    ProcessEngine processEngine = processEngineConfiguration
	            .buildProcessEngine();
	    System.out.println(processEngine);
	}

}
