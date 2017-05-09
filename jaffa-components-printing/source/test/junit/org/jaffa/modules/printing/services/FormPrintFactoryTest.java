package org.jaffa.modules.printing.services;

import org.jaffa.modules.printing.services.exceptions.EngineInstantiationException;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FormPrintFactoryTest {

    @Test
    public void testExternalFactory() throws EngineInstantiationException {
        IFormPrintEngine fopEngine = mock(IFormPrintEngine.class);
        IFormPrintFactory factory = mock(IFormPrintFactory.class);
        when(factory.newInstance("FOP")).thenReturn(fopEngine);
        FormPrintFactory.setExternalFactory(factory);

        IFormPrintEngine engine = FormPrintFactory.newInstance("FOP");

        assertNotNull(engine);
        assertSame(fopEngine, engine);
    }

}
