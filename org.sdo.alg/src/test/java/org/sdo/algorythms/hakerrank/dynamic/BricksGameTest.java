package org.sdo.algorythms.hakerrank.dynamic;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

/**
 * Created by dsemenov
 * Date: 10/28/16.
 */
public class BricksGameTest {

  @Rule
  public final SystemOutRule systemOutRule = new SystemOutRule().mute().enableLog();
  @Rule
  public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

  @Test
  public void test1() throws Exception {
    systemInMock.provideLines(
            "2",
            "5",
            "999 1 1 1 0",
            "5",
            "0 1 1 1 999");
    BricksGame.main(new String[]{});

    String result = systemOutRule.getLog();

    Assertions.assertThat(result).isEqualTo(
            "1001\n999\n"
    );
  }

  @Test
  public void test2() throws Exception {
    systemInMock.provideLines(
            "1",
            "3",
            "1 1 999");
    BricksGame.main(new String[]{});

    String result = systemOutRule.getLog();

    Assertions.assertThat(result).isEqualTo(
            "1001\n"
    );
  }

  @Test
  public void test3() throws Exception {
    systemInMock.provideLines(
            "1",
            "5",
            "0 1 1 1 999");
    BricksGame.main(new String[]{});

    String result = systemOutRule.getLog();

    Assertions.assertThat(result).isEqualTo(
            "999\n"
    );
  }

}
