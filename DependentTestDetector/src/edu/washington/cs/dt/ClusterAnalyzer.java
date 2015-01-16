package edu.washington.cs.dt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import apple.laf.JRSUIConstants.ShowArrows;
import edu.washington.cs.dt.util.Files;
import edu.washington.cs.dt.util.Globals;
import edu.washington.cs.dt.util.PermutationGenerator;
import edu.washington.cs.dt.util.Utils;

public class ClusterAnalyzer {

	public final String[] files;
	
	public ClusterAnalyzer(String[] files) {
		this.files = files;
	}
	
	public ClusterAnalyzer(String dir) {
		Collection<File> allfiles = Files.listFiles(new File(dir), null, false);
		files = new String[allfiles.size()];
		int index = 0;
		for(File f : allfiles) {
			files[index] = f.getAbsolutePath();
			index++;
		}
	}
	static HashSet<String> ignoredStaticFields;
	static
	{
		ignoredStaticFields = new HashSet<String>();
		ignoredStaticFields.add("java.lang.System.out");
		ignoredStaticFields.add("java.lang.System.err");
		ignoredStaticFields.add("java.lang.Void.TYPE");
		ignoredStaticFields.add("junit.runner.BaseTestRunner.fPreferences");
	}
	
	public Collection<Set<String>> generateClusters() {
		Collection<Set<String>> clusters = new LinkedList<Set<String>>();
		
		Map<String, Set<String>> accessedFields = new LinkedHashMap<String, Set<String>>();
		for(String file : files) {
			Utils.checkTrue(!accessedFields.containsKey(file), "already contained? " + file);
			Set<String> content = new LinkedHashSet<String>();
			content.addAll(Files.readWholeNoExp(file));
			content.removeAll(ignoredStaticFields);
			accessedFields.put(file.substring(file.lastIndexOf('/') + 1).replace(".txt", ""), content);
		}
		
		//perform clustering
		for(String file : accessedFields.keySet()) {
			Set<String> fields = accessedFields.get(file);
			
			Set<String> matched = null;
			for(Set<String> cluster : clusters) {
				boolean intersect = false;
				//find which
				for(String f : cluster) {
					Set<String> otherFields = accessedFields.get(f);
					//if two are interecting
					if(Utils.intersect(fields, otherFields)) {
						intersect = true;
					}
				}
				if(intersect) {
					cluster.add(file);
					matched = cluster;
				}
				
//				if(matched != null) {
//					break;
//				}
			}
			if(matched == null) {
				matched = new LinkedHashSet<String>();
				matched.add(file);
				clusters.add(matched);
			}
		}
		
		return clusters;
	}
	
	public void showBasicInfo(Collection<Set<String>> clusters, int i) {
		int cNo = clusters.size();
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		int avg = 0;
		int total = 0;
		for(Set<String> s : clusters) {
			total+=s.size();
			if(s.size() > max) {
				max = s.size();
			}
			if(s.size() < min) {
				min = s.size();
			}
		}
		avg = total / cNo;
		
//		System.out.println("Number of clusters: " + cNo);
//		System.out.println("  cluster max num: " + max);
//		System.out.println("  cluster min num: " + min);
//		System.out.println("  cluster avg num: " + avg);
//		System.out.println("  cluster total num: " + total);
		System.out.println("NumberClusters,MaxClusterSize,MinClusterSize,AvgClusterSize,TotalSize,NTuples");
		System.out.println(cNo+","+max+","+min+","+avg+","+total+","+i);
	}
	public static void main(String[] args) throws IOException {
		ClusterAnalyzer analyzer = new ClusterAnalyzer(args[0]);
		Collection<Set<String>> clusters = analyzer.generateClusters();
//		File outputDir = new File(args[1]);
//		outputDir.mkdir();
		int i = 0;
		PrintWriter pw = new PrintWriter(new FileWriter(args[1]),false);

		for(Set<String> s : clusters)
		{
			if(s.size() <= 1)
				continue;
			int total = s.size();
			ArrayList<String> tests = new ArrayList<>(s);
			PermutationGenerator generator = new PermutationGenerator(total, 2);
			while(generator.hasNext()) {
				/* get a list of test to run */
				int[] testIndices = generator.getNext();
				pw.println(tests.get(testIndices[0])+","+tests.get(testIndices[1]));
				i++;
				if(i % 10000 == 0)
				{
//					System.out.println("Processed: "+ i);
					pw.flush();
				}
			}
			
		}
		pw.flush();
		pw.close();
		analyzer.showBasicInfo(clusters,i);
	}
}