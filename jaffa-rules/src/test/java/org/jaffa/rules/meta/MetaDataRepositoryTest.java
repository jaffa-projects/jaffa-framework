package org.jaffa.rules.meta;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MetaDataRepositoryTest {
    @Test
    public void isClassOnWhiteList() {
        assertTrue(MetaDataRepository.instance().isClassOnWhiteList("org.jaffa.ValidClassName"));
    }

    @Test
    public void isClassOnWhiteList_NullClassName() {
        assertFalse(MetaDataRepository.instance().isClassOnWhiteList(null));
    }

    @Test
    public void isClassOnWhiteList_UnsafeClassName() {
        assertFalse(MetaDataRepository.instance().isClassOnWhiteList("org.jaffa.ValidClassName<script src=UNSAFE/>"));
    }

    @Test
    public void isClassOnWhiteList_InvalidClassName() {
        assertFalse(MetaDataRepository.instance().isClassOnWhiteList("org.package.InvalidPackageClass"));
    }
}
