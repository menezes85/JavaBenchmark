package com.javabenchmark.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * Benchmark de Map:
 * - Apis Java 8 x Classics Data: 02/01/2019
 * - Iterar sobre um map a partir de diversas formas.
 * 
 * @author CONDUCTOR\william.menezes
 *
 */
public class MapBenchmark {

	long startTime = 0;// System.currentTimeMillis();
	long endTime = 0;// System.currentTimeMillis();
	long totalExecutionTimeStart = 0;
	long totalExecutionTimeEnd = 0;

	List<Long> nonConcurrentResults = new ArrayList<Long>();
	List<Long> concurrentResults = new ArrayList<Long>();
	Map<Integer, Integer> tokens = new HashMap<>();

	public static void main(String[] args) {

		MapBenchmark t = new MapBenchmark();
		t.benchMark(true, true, true, true, true, true, true, true, 10);
		//t.benchMark(true, true, true, true, true, true, true, true, 300);
		//t.benchMark(true, true, true, true, true, true, true, true, 3000);
		//t.benchMark(true, true, true, true, true, true, true, true, 30000);

	}


	private void print2(String msg) {
		
		System.out.println(msg);

	}


	private void resetNanoClock() {
		startTime = 0;
		endTime = 0;
		startTime = System.nanoTime();
	}
	private void stopNanoClock() {
		endTime = System.nanoTime();
	}


	private long duration(long end, long start) {
		return (end - start);
	}

	long classicEntrySetDuration;
	long lambdaDuration;
	long streamAPIDuration;
	long paralelStreamDuration;
	long lambdaEntrySetDuration;
	long keysetDuration;
	long classicIteratorDuration;
	long iteratorWithKeySetDuration;
	private void benchMark(boolean classicEntrySet, boolean lambda, boolean streamAPI, boolean paralelStream,
			boolean lambdaEntrySet, boolean keyset, boolean classicIterator, boolean iteratorWithKeySet, int size) {

		// Tamanho do Teste
		criaHashMap(size);
		totalExecutionTimeStart = 0;
		totalExecutionTimeStart = System.currentTimeMillis();

		if (classicEntrySet) {
			resetNanoClock();
			for (Map.Entry<Integer, Integer> entry : tokens.entrySet()) {
				// tokens.forEach( (k,v) -> [do something with key and value] );
				//System.out.println(entry.getKey() + "/" + entry.getValue());
			}
			stopNanoClock();
			classicEntrySetDuration = duration(endTime, startTime);
			nonConcurrentResults.add(classicEntrySetDuration);

		}

		// forEach Java8
		if (lambda) {
			resetNanoClock();
			tokens.forEach((k, v) -> 
			System.out.println("key" + k + "/ Value:" + v)
			
					);
			stopNanoClock();
			lambdaDuration = duration(endTime, startTime);
			nonConcurrentResults.add(lambdaDuration);

		}

		// Stream API
		if (streamAPI) {
			tokens.entrySet().stream().forEach((e) -> {
				resetNanoClock();
			///	System.out.println("key: " + e.getKey() + ": value: " + e.getValue());
				stopNanoClock();

			});
			streamAPIDuration = duration(endTime, startTime);
			concurrentResults.add(streamAPIDuration);
	
		}

		// Stream API paralel
		if (paralelStream) {
			tokens.entrySet().stream().parallel().forEach((e) -> {
				resetNanoClock();
				//System.out.println("key" + e.getKey() + ": value" + e.getValue());
				stopNanoClock();

			});
			paralelStreamDuration = duration(endTime, startTime);
			concurrentResults.add(paralelStreamDuration);
		}
		// EntrySet Iterator
		if (lambdaEntrySet) {
			resetNanoClock();
			tokens.entrySet().forEach(entry -> {
			//	System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
			});
			stopNanoClock();
			lambdaEntrySetDuration = duration(endTime, startTime);
			nonConcurrentResults.add(lambdaEntrySetDuration);
			
		}
		// Keyset
		if (keyset) {
			resetNanoClock();
			for (Integer key : tokens.keySet()) {
			//	System.out.println("Key : " + key + " value : " + tokens.get(key));
			}
			stopNanoClock();
			keysetDuration = duration(endTime, startTime);
			nonConcurrentResults.add(keysetDuration);
		}

		if (classicIterator) {
			resetNanoClock();
			Iterator<Map.Entry<Integer, Integer>> iterator = tokens.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<Integer, Integer> entry = iterator.next();
			//	System.out.println("Key : " + entry.getKey() + " value : " + entry.getValue());
			}
			stopNanoClock();
			classicIteratorDuration = duration(endTime, startTime);
			nonConcurrentResults.add(classicIteratorDuration);

		}
		if (iteratorWithKeySet) {
			resetNanoClock();
			Iterator<Integer> iterator = tokens.keySet().iterator();
			while (iterator.hasNext()) {
				Integer key = iterator.next();
			//	System.out.println("Key : " + key + " value : " + tokens.get(key));
			}
			stopNanoClock();
			iteratorWithKeySetDuration  = duration(endTime, startTime);
			nonConcurrentResults.add(iteratorWithKeySetDuration);
		
		}
		//1e-9

		System.out.println("======================================================");
		System.out.println("=================== BenchMark ========================");
		System.out.println("======================================================");
		print2("Running Test with Map, Size: "+size);
		print2("-----------Non Concurrent ----------------------------------");
		print2("classicEntrySet: " + classicEntrySetDuration+ " nanoseconds.");
		print2("lambdaEntrySet: " + lambdaEntrySetDuration+ " nanoseconds.");
		print2("keyset: " + keysetDuration+ " nanoseconds.");
		print2("classicIteratorDuration: " + classicIteratorDuration+ " nanoseconds.");
		print2("IteradorWithKeySet: " + iteratorWithKeySetDuration+ " nanoseconds.");
		print2("ForEach Java8:: " + lambdaDuration+ " miliseconds.");
		print2("-----------Concurrent ----------------------------------");
		print2("StreamAPI:: " + streamAPIDuration+ " nanoseconds.");
		print2("StreamAPI Paralel: " + paralelStreamDuration+ " nanoseconds.");
		print2("-----------Concurrent ----------------------------------");
		print2("============================================");
		print2("============= Results =====================");
		print2("============================================");
		print2("Best Non Concurrent Time: "+ Collections.min(nonConcurrentResults) +" nanoseconds");
		print2("Best Concurrent Time: "+ Collections.min(concurrentResults) + " nanoseconds");
		print2("============================================");
		totalExecutionTimeEnd = 0;
		totalExecutionTimeEnd = System.currentTimeMillis();
		long duration = (totalExecutionTimeEnd - totalExecutionTimeStart);
		print2("Total Execution Time: " + duration + " mili seconds.");

	}

	private void criaHashMap(int Size) {

		for (int i = 1; i <= Size; i++) {
			tokens.put(i, i);
		}

	}

}
