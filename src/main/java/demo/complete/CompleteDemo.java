package demo.complete;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

public class CompleteDemo {

	@Test
	public void deploy() {
		try {
			complete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void complete() {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		String taskId = "2504";  
		processEngine.getTaskService().complete(taskId);  
		System.out.println("完成任务：任务ID:" + taskId);  
	}
}
