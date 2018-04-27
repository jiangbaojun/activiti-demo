package demo.crud;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class CRUDDemo {

	@Test
	public void crud() {
		try {
//			query();
			findTask();
//			findHistoryTask();
//			delete();
//			deleteInstance();
//			deleteDeployment();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void query() {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		List<ProcessDefinition> list = processEngine.getRepositoryService()  
	            .createProcessDefinitionQuery()  
	            // 查询条件  
	            .processDefinitionKey("MyProcess")// 按照流程定义的key  
	            // .processDefinitionId("helloworld")//按照流程定义的ID  
	            .orderByProcessDefinitionVersion().desc()// 排序  
	            // 返回结果  
	            // .singleResult()//返回惟一结果集  
	            // .count()//返回结果集数量  
	            // .listPage(firstResult, maxResults)  
	            .list();// 多个结果集  
	      
	    if(list!=null && list.size()>0){  
	        for(ProcessDefinition pd:list){  
	            System.out.println("流程定义的ID："+pd.getId());  
	            System.out.println("流程定义的名称："+pd.getName());  
	            System.out.println("流程定义的Key："+pd.getKey());  
	            System.out.println("流程定义的部署ID："+pd.getDeploymentId());  
	            System.out.println("流程定义的资源名称："+pd.getResourceName());  
	            System.out.println("流程定义的版本："+pd.getVersion());  
	            System.out.println("########################################################");  
	        }  
	    }  
	}
	
	public void findHistoryTask(){  
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	    String processInstanceId="2501";  
	    List<HistoricTaskInstance> list = processEngine.getHistoryService()//与历史数据（历史表）相关的service  
	            .createHistoricTaskInstanceQuery()//创建历史任务实例查询  
	            .processInstanceId(processInstanceId)  
//	              .taskAssignee(taskAssignee)//指定历史任务的办理人  
	            .list();  
	    if(list!=null && list.size()>0){  
	        for(HistoricTaskInstance hti:list){  
	            System.out.println(hti.getId()+"    "+hti.getName()+"    "+hti.getProcessInstanceId()+"   "+hti.getStartTime()+"   "+hti.getEndTime()+"   "+hti.getDeleteReason()+"   "+hti.getDurationInMillis());  
	            System.out.println("################################");  
	        }  
	    }     
	  
	}
	public void findTask(){  
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		String assignee = "李四";
        List<Task> list = processEngine.getTaskService()// 与正在执行的认为管理相关的Service
                .createTaskQuery()// 创建任务查询对象
                .taskAssignee(assignee)// 指定个人认为查询，指定办理人
                .list();
        if (list != null && list.size() > 0) {
            for (Task task:list) {
                System.out.println("任务ID:"+task.getId());
                System.out.println("任务名称:"+task.getName());
                System.out.println("任务的创建时间"+task);
                System.out.println("任务的办理人:"+task.getAssignee());
                System.out.println("流程实例ID:"+task.getProcessInstanceId());
                System.out.println("执行对象ID:"+task.getExecutionId());
                System.out.println("流程定义ID:"+task.getProcessDefinitionId());
                System.out.println("#################################");
            }
        }
	}
	
	public void delete(){  
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	    //部署对象ID  
	    String deploymentId = "30001";  
	    processEngine.getRepositoryService()//获取流程定义和部署对象相关的Service  
	        .deleteDeployment(deploymentId,true);  
	      
	    System.out.println("删除成功~~~");//使用部署ID删除流程定义,true表示级联删除  
	}  
	
	public void deleteInstance(){  
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	    //部署流程对象ID  
	    String instanceId = "52505";  
	    processEngine.getRuntimeService().deleteProcessInstance(instanceId, "nothing");
	      
	    System.out.println("流程实例删除成功~~~");//使用流程ID删除流程定义
	}  
	public void deleteDeployment(){  
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	    //部署对象ID  
	    String deploymentId = "5001";  
	    processEngine.getRepositoryService().deleteDeployment(deploymentId,true);
	      
	    System.out.println("部署删除成功~~~");//使用部署ID删除流程定义,true表示级联删除  
	}  
}
