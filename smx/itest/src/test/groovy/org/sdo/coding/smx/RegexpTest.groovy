package org.sdo.coding.smx;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: dsemenov
 * Date: 9/29/13
 * Time: 11:36 PM
 */
public class RegexpTest {
    @Test
    void test() {
        def str = "[sshexec] [ 215] [Active     ] [            ] [       ] [   50] Apache ServiceMix :: Bundles :: saxon (9.1.0.8_1)\n" +
                "  [sshexec] [ 216] [Active     ] [Created     ] [       ] [   50] Apache ServiceMix :: Components :: WS-Notification Service Engine (2011.02.1.fuse-04-06)\n" +
                "  [sshexec] [ 217] [Active     ] [            ] [       ] [   60] camel-groovy (2.8.0.fuse-03-06)\n" +
                "  [sshexec] [ 218] [Active     ] [            ] [       ] [   60] words (1.0.0)";
        def wordsBundle = str =~ /(?m)^(.*Started.*words \(1.0.0\).*)$/
        assert  wordsBundle.size() == 0
    }

    @Test
    void test2() {
        def str = "[sshexec] [ 215] [Active     ] [            ] [       ] [   50] Apache ServiceMix :: Bundles :: saxon (9.1.0.8_1)\n" +
                "  [sshexec] [ 216] [Active     ] [Created     ] [       ] [   50] Apache ServiceMix :: Components :: WS-Notification Service Engine (2011.02.1.fuse-04-06)\n" +
                "  [sshexec] [ 217] [Active     ] [            ] [       ] [   60] camel-groovy (2.8.0.fuse-03-06)\n" +
                "  [sshexec] [ 218] [Active     ] [Started     ] [       ] [   60] words (1.0.0)";
        def wordsBundle = str =~ /(?m)^(.*Started.*words \(1.0.0\).*)$/
        assert  wordsBundle.size() == 1
    }
}
