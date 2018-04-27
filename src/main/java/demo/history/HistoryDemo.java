package demo.history;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.junit.Test;

public class HistoryDemo {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	@Test
	public void crud() {
		findHisProcessInstance();
//		findHisActivitiList();
//		findHisTaskList();
//		findHisVariablesList();
	}
	//已经启动的流程，
	public void findHisProcessInstance(){     
	    List<HistoricProcessInstance> list = processEngine.getHistoryService()  
	            .createHistoricProcessInstanceQuery()  
	            .processDefinitionId("HelloWorld:2:10004")//流程定义ID  
	            .list();  
	      
	    if(list != null && list.size()>0){  
	        for(HistoricProcessInstance hi:list){  
	            System.out.println(hi.getId()+"   "+hi.getStartTime()+"   "+hi.getEndTime());  
	        }  
	    }  
	} 
	
	//act_hi_actinst
	public void findHisActivitiList(){  
	    String processInstanceId = "10005";  
	    List<HistoricActivityInstance> list = processEngine.getHistoryService()  
	            .createHistoricActivityInstanceQuery()  
	            .processInstanceId(processInstanceId)  
	            .list();  
	    if(list != null && list.size()>0){  
	        for(HistoricActivityInstance hai : list){  
	            System.out.println(hai.getId()+"  "+hai.getActivityName());  
	        }  
	    }  
	}  
	
	public void findHisTaskList(){  
	    String processInstanceId = "10005";  
	    List<HistoricTaskInstance> list = processEngine.getHistoryService()  
	            .createHistoricTaskInstanceQuery()  
	            .processInstanceId(processInstanceId)  
	            .list();  
	    if(list!=null && list.size()>0){  
	        for(HistoricTaskInstance hti:list){  
	            System.out.println(hti.getId()+"    "+hti.getName()+"   "+hti.getClaimTime());  
	        }  
	    }  
	}  
	
	public void findHisVariablesList(){  
	    String processInstanceId = "10005";  
	    List<HistoricVariableInstance> list = processEngine.getHistoryService()  
	            .createHistoricVariableInstanceQuery()  
	            .processInstanceId(processInstanceId)  
	            .list();  
	    if(list != null && list.size()>0){  
	        for(HistoricVariableInstance hvi:list){  
	            System.out.println(hvi.getId()+"    "+hvi.getVariableName()+"   "+hvi.getValue());  
	        }  
	    }  
	}  
}
