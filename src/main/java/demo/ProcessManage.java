package demo;

import java.util.Date;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

public class ProcessManage {
	
	@Test
	public void Test1() {
		ProcessManage p = new ProcessManage();
//		p.findProcessDefinition();
//		p.delteProcessDefinitionByKey();
//		p.findHistoryTask();
//		p.isProcessEnd();
//		p.setVariables();
//		p.getVariables();
		p.completeTask();
//		p.resolveTask();
	}

	/**
	 * 查询已部署流程
	 * @Author		姜宝俊
	 * @Group 		技术组
	 * @Worker	 	1861 
	 * @date 		2017年12月22日 下午4:06:36     
	 * @Company		Vstsoft
	 * @version 	V1.0
	 */
	public void findProcessDefinition() {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	    List<ProcessDefinition> list = processEngine.getRepositoryService()// 与流程定义和部署对象先相关的service
	            .createProcessDefinitionQuery()// 创建一个流程定义的查询
	            /** 指定查询条件，where条件 */
	            // .deploymentId(deploymentId) //使用部署对象ID查询
	            // .processDefinitionId(processDefinitionId)//使用流程定义ID查询
	            // .processDefinitionNameLike(processDefinitionNameLike)//使用流程定义的名称模糊查询

	            /* 排序 */
	            .orderByProcessDefinitionVersion().asc()
	            // .orderByProcessDefinitionVersion().desc()

	            /* 返回的结果集 */
	            .list();// 返回一个集合列表，封装流程定义
	    // .singleResult();//返回惟一结果集
	    // .count();//返回结果集数量
	    // .listPage(firstResult, maxResults);//分页查询

	    if (list != null && list.size() > 0) {
	        for (ProcessDefinition pd : list) {
	            System.out.println("流程定义ID:" + pd.getId());// 流程定义的key+版本+随机生成数
	            System.out.println("流程定义的名称:" + pd.getName());// 对应helloworld.bpmn文件中的name属性值
	            System.out.println("流程定义的key:" + pd.getKey());// 对应helloworld.bpmn文件中的id属性值
	            System.out.println("流程定义的版本:" + pd.getVersion());// 当流程定义的key值相同的相同下，版本升级，默认1
	            System.out.println("资源名称bpmn文件:" + pd.getResourceName());
	            System.out.println("资源名称png文件:" + pd.getDiagramResourceName());
	            System.out.println("部署对象ID：" + pd.getDeploymentId());
	            System.out.println("#########################################################");
	        }
	    }
	}
	/**
	 * 删除流程定义(删除key相同的所有不同版本的流程定义)
	 */
	public void delteProcessDefinitionByKey() {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	    // 流程定义的Key
	    String processDefinitionKey = "HelloWorld";
	    // 先使用流程定义的key查询流程定义，查询出所有的版本
	    List<ProcessDefinition> list = processEngine.getRepositoryService()
	            .createProcessDefinitionQuery()
	            .processDefinitionKey(processDefinitionKey)// 使用流程定义的key查询
	            .list();
	    // 遍历，获取每个流程定义的部署ID
	    if (list != null && list.size() > 0) {
	        for (ProcessDefinition pd : list) {
	            // 获取部署ID
	            String deploymentId = pd.getDeploymentId();
	            //        /*
	            //         * 不带级联的删除， 只能删除没有启动的流程，如果流程启动，就会抛出异常
	            //         */
	            //         processEngine.getRepositoryService().deleteDeployment(deploymentId);
	            
	            /**
	             * 级联删除 不管流程是否启动，都可以删除
	             */
	            processEngine.getRepositoryService().deleteDeployment(
	                    deploymentId, true);

	        }

	    }
	}
	
	/**
	 * 查询历史
	 * @Author		姜宝俊
	 * @Group 		技术组
	 * @Worker	 	1861 
	 * @date 		2017年12月22日 下午5:19:20     
	 * @Company		Vstsoft
	 * @version 	V1.0
	 */
	public void findHistoryTask(){  
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	    String processInstanceId="42505";  
	    List<HistoricTaskInstance> list = processEngine.getHistoryService()//与历史数据（历史表）相关的service  
	            .createHistoricTaskInstanceQuery()//创建历史任务实例查询  
	            .processInstanceId(processInstanceId)  
//	              .taskAssignee(taskAssignee)//指定历史任务的办理人  
	            .list();  
	    if(list!=null && list.size()>0){  
	        for(HistoricTaskInstance hti:list){  
	            System.out.println(hti.getId()+"    "+hti.getName()+"    "+hti.getProcessInstanceId()+"   "+hti.getStartTime()+"   "+hti.getEndTime()+"   "+hti.getDurationInMillis());  
	            System.out.println("################################");  
	        }  
	    }     
	  
	}
	/**判断流程状态*/
	public void isProcessEnd(){  
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	    String processInstanceId =  "42505";  
	    ProcessInstance pi = processEngine.getRuntimeService()//表示正在执行的流程实例和执行对象  
	            .createProcessInstanceQuery()//创建流程实例查询  
	            .processInstanceId(processInstanceId)//使用流程实例ID查询  
	            .singleResult();  
	      
	    if(pi==null){  
	        System.out.println("流程已经结束");  
	    }  
	    else{  
	        System.out.println("流程没有结束");  
	    }  
	      
	}
	//设置流程-任务变量
	public void setVariables() {  
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	    // 与任务相关的service,正在执行的service  
	    TaskService taskService = processEngine.getTaskService();  
	  
	    // 任务ID  
	    String taskId = "47511";  
	  
	    // 1.设置流程变量，使用基本数据类型  
	    taskService.setVariable(taskId, "请假天数", 7);// 与任务ID邦德  
	    taskService.setVariable(taskId, "请假日期", new Date());  
	    taskService.setVariableLocal(taskId, "请假原因", "回去探亲，一起吃个饭123");  
	      
	    System.out.println("设置变量成功！");  
	  
	}
	/** 
	 * 获取流程变量 
	 */  
	public void getVariables() {  
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	    // 与任务（正在执行的service）  
	    TaskService taskService = processEngine.getTaskService();  
	    // 任务Id  
	    String taskId = "47511";  
	    // 1.获取流程变量，使用基本数据类型  
	    Integer days = (Integer) taskService.getVariable(taskId, "请假天数");  
	    Date date = (Date) taskService.getVariable(taskId, "请假日期");  
	    String reason = (String) taskService.getVariable(taskId, "请假原因");  
	  
	    System.out.println("请假天数：" + days);  
	    System.out.println("请假日期：" + date);  
	    System.out.println("请假原因：" + reason);  
	  
	}
	
	public void completeTask() {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		String taskId = "5004";  
		processEngine.getTaskService().complete(taskId);  
		System.out.println("完成任务：任务ID:" + taskId);  
	}
	
	public void resolveTask() {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		String taskId = "47511";  
        processEngine.getTaskService().resolveTask(taskId);  
        System.out.println("完成任务：任务ID:" + taskId);  
	}
}
