package org.sdo.algorythms.pi;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static spock.util.matcher.HamcrestMatchers.closeTo;

/**
 * Created with IntelliJ IDEA.
 * User: dsemenov
 * Date: 1/23/13
 * Time: 9:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class PiTest {
    @Test
    public void verify_that_pi_with_one_square_equals_3_4641() {
        assertThat(Pi.pi(1), closeTo(3.4641, 0.001));
    }

    @Test
    public void verify_that_we_can_calc_pi_with_more_stems() {
        assertThat(Pi.pi(2000), closeTo(3.1415, 0.0001));
    }

    @Test
    public void verify_that_i_can_calculate_pi_with_precision_0_000001() {
        assertThat(Pi.pi(0.001), closeTo(3.1415, 0.001));
    }
}
