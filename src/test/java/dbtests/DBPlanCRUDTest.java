package dbtests;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import data.Plan;
import data.db.DBPlan;


public class DBPlanCRUDTest {
	
	@Test
	public void testInsert(){
		String id = UUID.randomUUID().toString();
		Plan testPlan = new Plan.Builder().setId(id).withTitle("test").withDescription("test").withPlanNumber("number").withMap("Map").build();
		DBPlan.getInstance().insertPlan(testPlan);
		
		List<Plan> planList = DBPlan.getInstance().getAllPlans();
		for(Plan plan : planList){
			if(plan.getId().equals(id)) {
				Plan updatePlan = new Plan.Builder().withDescription("update").withPlanNumber("2").withTitle("updateTitle").setId(plan.getId()).build();
				DBPlan.getInstance().updatePlan(updatePlan);
				DBPlan.getInstance().deletePlan(updatePlan);
			}
		}
	}
	
	
	
	
}
