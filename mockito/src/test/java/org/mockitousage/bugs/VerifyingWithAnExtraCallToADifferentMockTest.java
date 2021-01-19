/*
 * Copyright (c) 2007 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */
package org.mockitousage.bugs;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.exceptions.verification.NeverWantedButInvoked;
import org.mockitousage.IMethods;
import org.mockitoutil.TestBase;

// see bug 138
public class VerifyingWithAnExtraCallToADifferentMockTest extends TestBase {

    @Mock IMethods mock;
    @Mock IMethods mockTwo;

    @Test
    public void shouldAllowVerifyingWhenOtherMockCallIsInTheSameLine() {
        // given
        when(mock.otherMethod()).thenReturn("foo");

        // when
        mockTwo.simpleMethod("foo");

        // then
        verify(mockTwo).simpleMethod(mock.otherMethod());
        try {
            verify(mockTwo, never()).simpleMethod(mock.otherMethod());
            fail();
        } catch (NeverWantedButInvoked e) {
        }
    }
}
