package org.sdo.algorythms.hakerrank.string;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

/**
 * Created by dsemenov
 * Date: 10/24/16.
 */
public class RichieRichTest {

  @Rule
  public final SystemOutRule systemOutRule = new SystemOutRule().mute().enableLog();
  @Rule
  public final TextFromStandardInputStream systemInMock
          = emptyStandardInputStream();

  @Test
  public void check_tricky_test_1() {
    systemInMock.provideLines("8 4", "11119111");
    RichieRich.main(new String[]{});

    String log = systemOutRule.getLog();
    Assertions.assertThat(log).isEqualTo("91199119\n");
  }

  @Test
  public void test2() {
    //
    systemInMock.provideLines("15 8", "128392759430124");
    RichieRich.main(new String[]{});
    Assertions.assertThat(systemOutRule.getLog()).isEqualTo("929394959493929\n");
  }

  @Test
  public void test3() {
    systemInMock.provideLines("4 1", "3943");
    RichieRich.main(new String[]{});
    Assertions.assertThat(systemOutRule.getLog()).isEqualTo("3993\n");
  }

  @Test
  public void test4() {
    systemInMock.provideLines("6 3", "092282");
    RichieRich.main(new String[]{});
    Assertions.assertThat(systemOutRule.getLog()).isEqualTo("992299\n");
  }

  @Test
  public void test4_1() {
    systemInMock.provideLines("7 3", "0925282");
    RichieRich.main(new String[]{});
    Assertions.assertThat(systemOutRule.getLog()).isEqualTo("9925299\n");
  }

  @Test
  public void test4_2() {
    systemInMock.provideLines("7 3", "0925292");
    RichieRich.main(new String[]{});
    Assertions.assertThat(systemOutRule.getLog()).isEqualTo("9929299\n");
  }

  @Test
  public void test4_3() {
    systemInMock.provideLines("7 3", "0825282");
    RichieRich.main(new String[]{});
    Assertions.assertThat(systemOutRule.getLog()).isEqualTo("9829289\n");
  }

  @Test
  public void test4_4() {
    systemInMock.provideLines("7 3", "9925299");
    RichieRich.main(new String[]{});
    Assertions.assertThat(systemOutRule.getLog()).isEqualTo("9999999\n");
  }

  @Test
  public void test4_5() {
    systemInMock.provideLines("6 1", "992299");
    RichieRich.main(new String[]{});
    Assertions.assertThat(systemOutRule.getLog()).isEqualTo("992299\n");
  }

  @Test
  public void test5() {
    systemInMock.provideLines("4 1", "0011");
    RichieRich.main(new String[]{});
    Assertions.assertThat(systemOutRule.getLog()).isEqualTo("-1\n");
  }

  @Test
  public void test5_2() {
    systemInMock.provideLines("4 2", "0011");
    RichieRich.main(new String[]{});
    Assertions.assertThat(systemOutRule.getLog()).isEqualTo("1111\n");
  }

  @Test
  public void test6() {
    systemInMock.provideLines("1 1", "0");
    RichieRich.main(new String[]{});
    Assertions.assertThat(systemOutRule.getLog()).isEqualTo("9\n");
  }

  @Test
  public void test7() {
    systemInMock.provideLines("1 0", "1");
    RichieRich.main(new String[]{});
    Assertions.assertThat(systemOutRule.getLog()).isEqualTo("1\n");
  }

  @Test
  public void test8() {
    systemInMock.provideLines("3 2", "123");
    RichieRich.main(new String[]{});
    Assertions.assertThat(systemOutRule.getLog()).isEqualTo("929\n");
  }

  @Test
  public void test9() {
    systemInMock.provideLines("3 3", "123");
    RichieRich.main(new String[]{});
    Assertions.assertThat(systemOutRule.getLog()).isEqualTo("999\n");
  }

  @Test
  public void test10() {
    systemInMock.provideLines("3 0", "929");
    RichieRich.main(new String[]{});
    Assertions.assertThat(systemOutRule.getLog()).isEqualTo("929\n");
  }

  @Test
  public void test10_1() {
    systemInMock.provideLines("7 4", "1111111");
    RichieRich.main(new String[]{});
    Assertions.assertThat(systemOutRule.getLog()).isEqualTo("9911199\n");
  }

  @Test
  public void test11() {
    systemInMock.provideLines("7 4", "9711319");
    RichieRich.main(new String[]{});
    Assertions.assertThat(systemOutRule.getLog()).isEqualTo("9991999\n");
  }

}