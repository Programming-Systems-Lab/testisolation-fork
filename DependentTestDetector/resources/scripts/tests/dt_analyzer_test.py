#!/usr/bin/python
#
# Script:
# Purpose:
# Author: Jochen Wuttke, wuttkej@gmail.com
# Date:

import unittest
import dt_analyzer

class DtAnalyzerTest(unittest.TestCase):
    def test_TestResultPASS(self):
        r = dt_analyzer.TestResult("my.Test.Name#PASS%#%#NOTHINGHERE")
        self.assertEqual("my.Test.Name", r.name)
        self.assertEqual("PASS", r.result)
        self.assertIsNone(r.stack_trace)

    def test_TestResultFAIL(self):
        r = dt_analyzer.TestResult("my.Test.Name#FAILURE%#%#this is a longish stacktrace")
        self.assertEqual("my.Test.Name", r.name)
        self.assertEqual("FAILURE", r.result)
        self.assertEqual("this is a longish stacktrace - ", r.stack_trace)

    def test_TestResultERROR(self):
        r = dt_analyzer.TestResult("my.Test.Name#ERROR%#%#this is a longish stacktrace")
        self.assertEqual("my.Test.Name", r.name)
        self.assertEqual("ERROR", r.result)
        self.assertEqual("this is a longish stacktrace - ", r.stack_trace)

    def test_load_result(self):
        results = dt_analyzer.load_results("one_each.output")
        self.assertEqual(3, len(results))
        t = results[0]
        self.assertIsNotNone(t)
        self.assertEqual( "FAILURE", t.result)
        t = results[2]
        self.assertIsNotNone(t)
        self.assertEqual( "PASS", t.result)
        t = results[1]
        self.assertIsNotNone(t)
        self.assertEqual( "ERROR", t.result )

    def test_filter_junit(self):
        j = "junit.framework.Assert.fail(Assert.java:47) - synoptic.tests.integration.EndToEndMainTests.data(EndToEndMainTests.java:57) - sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) - sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39) - sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25) - java.lang.reflect.Method.invoke(Method.java:597) - org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:44) - org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:15) - org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:41) - org.junit.runners.Parameterized.getParametersList(Parameterized.java:144) - org.junit.runners.Parameterized.<init>(Parameterized.java:130) - sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method) - sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:39) - sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:27) - java.lang.reflect.Constructor.newInstance(Constructor.java:513) - org.junit.internal.builders.AnnotatedBuilder.buildRunner(AnnotatedBuilder.java:31) - org.junit.internal.builders.AnnotatedBuilder.runnerForClass(AnnotatedBuilder.java:24) - org.junit.runners.model.RunnerBuilder.safeRunnerForClass(RunnerBuilder.java:57) - org.junit.internal.builders.AllDefaultPossibilitiesBuilder.runnerForClass(AllDefaultPossibilitiesBuilder.java:29) - org.junit.runners.model.RunnerBuilder.safeRunnerForClass(RunnerBuilder.java:57) - org.junit.internal.requests.ClassRequest.getRunner(ClassRequest.java:24) - org.junit.internal.requests.FilterRequest.getRunner(FilterRequest.java:33) - org.junit.runner.JUnitCore.run(JUnitCore.java:136) - edu.washington.cs.dt.util.JUnitTestExecutor.executeWithJUnit4Runner(JUnitTestExecutor.java:64) - edu.washington.cs.dt.util.TestRunnerWrapperFileInputs.main(TestRunnerWrapperFileInputs.java:45) - "
        s = "junit.framework.Assert.fail(Assert.java:47) - synoptic.tests.integration.EndToEndMainTests.data(EndToEndMainTests.java:57) - sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) - sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39) - sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25) - java.lang.reflect.Method.invoke(Method.java:597) - "
        self.assertEqual(s, dt_analyzer.filter_junit(j))

    def test_filter_clinit(self):
        i = "edu.umd.cs.findbugs.cloud.AbstractCloudTest.setUp(AbstractCloudTest.java:47) - "
        j = "edu.umd.cs.findbugs.ProjectStats.<clinit>(ProjectStats.java:68) - edu.umd.cs.findbugs.cloud.AbstractCloudTest.setUp(AbstractCloudTest.java:47) - "
        self.assertEqual(i, dt_analyzer.filter_junit(j))

if __name__ == "__main__":
    unittest.main()
