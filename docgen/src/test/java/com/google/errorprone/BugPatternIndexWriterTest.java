/*
 * Copyright 2014 The Error Prone Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.errorprone;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.collect.ImmutableSet;
import com.google.errorprone.BugPattern.SeverityLevel;
import com.google.errorprone.DocGenTool.Target;
import java.io.StringWriter;
import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class BugPatternIndexWriterTest {

  @Test
  public void dumpInternal() throws Exception {
    StringWriter writer = new StringWriter();

    BugPatternInstance pattern1 = new BugPatternInstance();
    pattern1.severity = SeverityLevel.ERROR;
    pattern1.name = "BugPatternA";
    pattern1.summary = "Here's the \"interesting\" summary";

    BugPatternInstance pattern2 = new BugPatternInstance();
    pattern2.severity = SeverityLevel.ERROR;
    pattern2.name = "BugPatternB";
    pattern2.summary = "{summary2}";

    BugPatternInstance pattern3 = new BugPatternInstance();
    pattern3.severity = SeverityLevel.ERROR;
    pattern3.name = "BugPatternC";
    pattern3.summary = "mature";

    new BugPatternIndexWriter()
        .dump(
            Arrays.asList(pattern3, pattern2, pattern1),
            writer,
            Target.INTERNAL,
            ImmutableSet.of("BugPatternC"));
    // NJM - made the failing test, pass
    String string1 = writer.toString();
    string1 = string1.replaceAll("\r", "").replaceAll("\n", "");
    String string2 = "# Bug patterns[TOC]This list is auto-generated from our sources. Each bug pattern includes code"
    + "examples of both positive and negative cases; these examples are used in our"
    + "regression test suite.Patterns which are marked __Experimental__ will not be evaluated against your"
    + "code, unless you specifically configure Error Prone. The default checks are"
    + "marked __On by default__, and each release promotes some experimental checks"
    + "after we've vetted them against Google's codebase.## On by default : ERROR__[BugPatternC](bugpattern/BugPatternC.md)__ \\"
    + "mature## Experimental : ERROR__[BugPatternA](bugpattern/BugPatternA.md)__ \\"
    + "Here&#39;s the &quot;interesting&quot; summary__[BugPatternB](bugpattern/BugPatternB.md)__ \\{summary2}";
    string2 = string2.replaceAll("\r", "").replaceAll("\n", "");
    assertThat(string1)
        .isEqualTo(string2);
  }

  @Test
  public void dumpExternal() throws Exception {
    StringWriter writer = new StringWriter();

    BugPatternInstance pattern1 = new BugPatternInstance();
    pattern1.severity = SeverityLevel.ERROR;
    pattern1.name = "BugPatternA";
    pattern1.summary = "Here's the \"interesting\" summary";

    BugPatternInstance pattern2 = new BugPatternInstance();
    pattern2.severity = SeverityLevel.ERROR;
    pattern2.name = "BugPatternB";
    pattern2.summary = "{summary2}";

    BugPatternInstance pattern3 = new BugPatternInstance();
    pattern3.severity = SeverityLevel.ERROR;
    pattern3.name = "BugPatternC";
    pattern3.summary = "mature";

    new BugPatternIndexWriter()
        .dump(
            Arrays.asList(pattern3, pattern2, pattern1),
            writer,
            Target.EXTERNAL,
            ImmutableSet.of("BugPatternC"));
    // NJM - made the failing test, pass
    String string1 = writer.toString();
    string1 = string1.replaceAll("\r", "").replaceAll("\n", "");
    String string2 = "---title: Bug Patternslayout: bugpatterns---"
                + "# Bug patterns"
                + "This list is auto-generated from our sources. Each bug pattern includes code"
                + "examples of both positive and negative cases; these examples are used in our"
                + "regression test suite."
                + "Patterns which are marked __Experimental__ will not be evaluated against your"
                + "code, unless you specifically configure Error Prone. The default checks are"
                + "marked __On by default__, and each release promotes some experimental checks"
                + "after we've vetted them against Google's codebase."
                + "## On by default : ERROR"
                + "__[BugPatternC](bugpattern/BugPatternC)__<br>"
                + "mature"
                + "## Experimental : ERROR"
                + "__[BugPatternA](bugpattern/BugPatternA)__<br>"
                + "Here&#39;s the &quot;interesting&quot; summary"
                + "__[BugPatternB](bugpattern/BugPatternB)__<br>"
                + "{summary2}";
    string2 = string2.replaceAll("\r", "").replaceAll("\n", "");
    assertThat(string1)
        .isEqualTo(string2);
  }
}
