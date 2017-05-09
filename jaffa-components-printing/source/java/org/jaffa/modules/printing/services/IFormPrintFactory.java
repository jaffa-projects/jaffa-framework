package org.jaffa.modules.printing.services;

/**
 * Created by dlengyel on 8/8/2016.
 */
public interface IFormPrintFactory {

    IFormPrintEngine newInstance(String engineType);
}
