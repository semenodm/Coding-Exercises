package org.sdo.algorythms.mostcommonsum;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class TreeMapHashAlgorithm implements Algorithm {

	private final class Entry implements Comparable<Entry> {
		private final int integer;
		private final int count;

		public Entry(int integer, int count) {
			super();
			this.integer = integer;
			this.count = count;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + count;
			result = prime * result + integer;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Entry other = (Entry) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (count != other.count)
				return false;
			if (integer != other.integer)
				return false;
			return true;
		}

		private TreeMapHashAlgorithm getOuterType() {
			return TreeMapHashAlgorithm.this;
		}

		public int compareTo(Entry o) {
			if (o == null) {
				return -1;
			}
			if (o == this) {
				return 0;
			}
			//It's not recommended to do. but in our case we can use it and not damage set structure
			return (o.count * 100) - (count * 100);
		}
	}

	public int extractCommonSum(int[] input) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		TreeSet<Entry> mapProduct = new TreeSet<Entry>();
		for (int i = 0; i < input.length; i++) {
			if (map.containsKey(input[i])) {
				Integer count = map.get(input[i]);
				//mapProduct.remove(new Entry(input[i], count));
				count++;
				map.put(input[i], count);				
				mapProduct.add(new Entry(input[i], count));
			} else {
				map.put(input[i], 1);
				mapProduct.add(new Entry(input[i], 1));
			}
		}
		
		return mapProduct.first().count * mapProduct.first().integer;
	}
}
