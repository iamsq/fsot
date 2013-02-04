package org.fsot;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.filter.AssignableTypeFilter;

public class JUnitHelper {
	
	public enum JunitVersion {JUnit3, JUnit4};

	public static void printTests(ClassLoader cl, JunitVersion junitVersion, boolean printMethods) throws Exception {
		
		String[] tests = getTestClassNames(cl, junitVersion);
		
		System.out.println("Found Test Classes = " + tests.length);
		
		for (String testClassName : tests) {
			System.out.println(testClassName);
			
			if(printMethods)
			{
				Method[] methods = cl.loadClass(testClassName).getDeclaredMethods();
				printTestMethods(methods, junitVersion);
			}
		}
	}
	
	public static String[] getTestClassNames(ClassLoader cl, JunitVersion junitVersion) {
		System.out.println("Getting list of tests...");
		if(junitVersion==JunitVersion.JUnit3)
		{
			return getJUnit3Tests(cl);
		}
		
		if(junitVersion==JunitVersion.JUnit4)
		{
			return getJUnit4Tests(cl);
		}
		
		return null;
	}

	private static String[] getJUnit3Tests(ClassLoader cl) {

		ClassPathScanningCandidateComponentProvider provider;
		provider = new ClassPathScanningCandidateComponentProvider(false);

		provider.setResourceLoader(new PathMatchingResourcePatternResolver(cl));

		// include only sub type of JUnit 3 tests
		provider.addIncludeFilter(new AssignableTypeFilter(
				junit.framework.TestCase.class));
		provider.addIncludeFilter(new AssignableTypeFilter(
				junit.framework.TestSuite.class));
		

		// package to scan in
		Set<BeanDefinition> components = provider.findCandidateComponents("");
		return generateTestList(cl,components, JunitVersion.JUnit3);
	}

	private static String[] getJUnit4Tests(ClassLoader cl) {

		ClassPathScanningCandidateComponentProvider provider;
		provider = new ClassPathScanningCandidateComponentProvider(false);

		provider.setResourceLoader(new PathMatchingResourcePatternResolver(cl));

		// JUnit 4 tests are sub type of Objects
		provider.addIncludeFilter(new AssignableTypeFilter(Object.class));

		// package to scan in
		Set<BeanDefinition> components = provider.findCandidateComponents("");
		
		return generateTestList(cl,components, JunitVersion.JUnit4);
	}
	
	private static String[] generateTestList(ClassLoader cl,Set<BeanDefinition> components,JunitVersion junitVersion)
	{
		Set<String> tests = new HashSet<String>();
		
		
		for (BeanDefinition component : components) { 
			//add test class name to list
			
			String testClassName = component.getBeanClassName();

			//Identifying JUnit 4 tests requires an additional step of checking the methods
			if(JunitVersion.JUnit4==junitVersion)
			{
				//System.out.println(testClassName);
				try {
					for (Method method : cl.loadClass(testClassName).getDeclaredMethods()) {
						if (method.isAnnotationPresent(org.junit.Test.class)) {
							tests.add(testClassName);
							break;
						}
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
			}else{
				tests.add(testClassName);
			}
		}
		
		return tests.toArray(new String[0]);
	}
	
	private static void printTestMethods(Method[] methods, JunitVersion junitVersion)
	{
		for (Method method : methods) {
			
			if(junitVersion==JunitVersion.JUnit3)
			{
				System.out.println("\t" + method.getName());
			}
			
			if(junitVersion==JunitVersion.JUnit4)
			{
				if (method.isAnnotationPresent(org.junit.Test.class)) {
					System.out.println("\t" + method.getName());
				}
			}
		}
	}

}
