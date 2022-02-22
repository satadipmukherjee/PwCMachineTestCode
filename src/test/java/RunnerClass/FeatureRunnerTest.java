package RunnerClass;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;

@CucumberOptions (
		features="src/main/resources/FeatureFiles",
		glue={"StepDefinitions"},
		monochrome=true,
		dryRun=false,
		plugin= {"pretty",
				"html:target/cucumber-reports/CucumberReport.html",
				"json:target/cucumber-reports/CucumberTestReport.json",
				"rerun:target/cucumber-reports/rerun.txt"}
		)
public class FeatureRunnerTest {

	private TestNGCucumberRunner testNGCucumberRunner;
	
	@BeforeClass(alwaysRun=true)
	public void setupClass()
	{
		testNGCucumberRunner=new TestNGCucumberRunner(this.getClass());
	}
	
	@Test(groups="CucumberTest",description="TestNG Test to execute Feature Files",dataProvider="scenarios")
	public void scenario(PickleWrapper pickle,FeatureWrapper feature)
	{
		testNGCucumberRunner.runScenario(pickle.getPickle());
	}
	
	@DataProvider
	public Object[][] scenarios()
	{
		return testNGCucumberRunner.provideScenarios();
	}
	
	@AfterClass(alwaysRun=true)
	public void TearDownClass()
	{
		testNGCucumberRunner.finish();
	}
}
