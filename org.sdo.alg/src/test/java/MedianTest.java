import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class MedianTest {

	@Test
	public void test() {
		System.out.println(16 / 2);
		System.out.println(16 >>> 1);

		List<Integer> asList = Arrays.asList(4, 5, 6, 7);

		System.out.println("Median element is " + asList.get((asList.size() >>> 1) - 1));

		System.out.println("Median element is " + asList.get((asList.size()) / 2));
	}

}
