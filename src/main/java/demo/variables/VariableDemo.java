package demo.variables;

import java.util.Date;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class VariableDemo {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	@Test
	public void crud() {
//		setProcessVariables();
//		getProcessVariables();
		getHistoryProcessVariables();
	}
	
	public void setProcessVariables(){  
	    String processInstanceId = "2501";//流程实例ID  
	    String assignee = "李四";//任务办理人  
	    TaskService taskService = processEngine.getTaskService();//获取任务的Service，设置和获取流程变量  
	    //查询当前办理人的任务ID  
	    Task task = taskService.createTaskQuery()  
	            .processInstanceId(processInstanceId)//使用流程实例ID  
	            .taskAssignee(assignee)//任务办理人  
	            .singleResult();  
	      
	    //设置流程变量【基本类型】  
	    taskService.setVariable(task.getId(), "请假人", assignee);  
	    taskService.setVariableLocal(task.getId(), "请假天数",5);  
	    taskService.setVariable(task.getId(), "请假日期", new Date());  
	    
	    //设置流程变量【javabean类型】  
	    Person p = new Person();  
	    p.setId(1);  
	    p.setName("小强");  
	    taskService.setVariable(task.getId(), "人员信息", p);  
	}  
	
	public void getProcessVariables(){  
	    String processInstanceId = "2501";//流程实例ID  
	    String assignee = "李四";//任务办理人  
	    TaskService taskService = processEngine.getTaskService();  
	    //获取当前办理人的任务ID  
	    Task task = taskService.createTaskQuery()  
	            .processInstanceId(processInstanceId)  
	            .taskAssignee(assignee)  
	            .singleResult();  
	     if(task==null) {
	    	 System.out.println("任务不存在！");
	     }
	    //获取流程变量【基本类型】  
	    String person = (String) taskService.getVariable(task.getId(), "请假人");  
	    Integer day = (Integer) taskService.getVariableLocal(task.getId(), "请假天数");  
	    Date date = (Date) taskService.getVariable(task.getId(), "请假日期");  
	    System.out.println(person+"	"+day+"	"+date);  
	    
	    //获取流程变量【javaBean类型】  
	    Person p = (Person) taskService.getVariable(task.getId(), "人员信息");  
	    System.out.println(p.getId()+"	"+p.getName());  
	    System.out.println("获取成功~~"); 
	      
	}
	
	public void getHistoryProcessVariables(){  
	    List<HistoricVariableInstance> list = processEngine.getHistoryService()  
	            .createHistoricVariableInstanceQuery()//创建一个历史的流程变量查询  
	            .processInstanceId("2501")
//	            .variableName("请假天数")  
	            .list();  
	      
	    if(list != null && list.size()>0){  
	        for(HistoricVariableInstance hiv : list){  
	            System.out.println(hiv.getTaskId()+"  "+hiv.getVariableName()+"     "+hiv.getValue()+"      "+hiv.getVariableTypeName());  
	        }  
	    }                 
	}  
}
