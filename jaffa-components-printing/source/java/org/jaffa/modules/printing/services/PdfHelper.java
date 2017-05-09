/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2002-2012 JAFFA Development Group
 *
 *     This library is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU Lesser General Public
 *     License as published by the Free Software Foundation; either
 *     version 2.1 of the License, or (at your option) any later version.
 *
 *     This library is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public
 *     License along with this library; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * Redistribution and use of this software and associated documentation ("Software"),
 * with or without modification, are permitted provided that the following conditions are met:
 * 1.	Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.	Redistributions in binary form must reproduce the above copyright notice,
 * 	this list of conditions and the following disclaimer in the documentation
 * 	and/or other materials provided with the distribution.
 * 3.	The name "JAFFA" must not be used to endorse or promote products derived from
 * 	this Software without prior written permission. For written permission,
 * 	please contact mail to: jaffagroup@yahoo.com.
 * 4.	Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 * 	appear in their names without prior written permission.
 * 5.	Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
 *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */
package org.jaffa.modules.printing.services;

import org.apache.log4j.Logger;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Rectangle;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import org.jaffa.modules.printing.services.exceptions.FormPrintException;
import org.jaffa.modules.printing.services.exceptions.PdfProcessingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * This class provides PDF helper utilities.
 * @author DennisL
 */
public class PdfHelper {

    private final static Logger log = Logger.getLogger(PdfHelper.class);

    /**
     * Scale the pages of the input pdfOutput document to the given pageSize.
     * @param pdfOutput The PDF document to rescale, in the form of a ByteArrayOutputStream.
     * @param pageSize The new page size to which to scale to PDF document, e.g. "A4".
     * @param noEnlarge If true, center pages instead of enlarging them.
     *        Use noEnlarge if the new page size is larger than the old one
     *        and the pages should be centered instead of enlarged.
     * @param preserveAspectRatio If true, the aspect ratio will be preserved.
     * @return The PDF document with its pages scaled to the input pageSize.
     */
    public static byte[] scalePdfPages(byte[] pdfOutput, String pageSize, boolean noEnlarge, boolean preserveAspectRatio) throws FormPrintException {
        if (pageSize == null || pdfOutput == null) {
            return pdfOutput;
        }

        // Get the dimensions of the given pageSize in PostScript points.
        // A PostScript point is a 72th of an inch.
        float dimX;
        float dimY;
        Rectangle rectangle;
        try {
            rectangle = PageSize.getRectangle(pageSize);
        } catch (Exception ex) {
            FormPrintException e = new PdfProcessingException("scalePdfPages  - Invalid page size = " + pageSize + "  ");
            log.error(" scalePdfPages  - Invalid page size: " + pageSize + ".  " + ex.getMessage() + ". ");
            throw e;
        }
        if (rectangle != null) {
            dimX = rectangle.getWidth();
            dimY = rectangle.getHeight();
        } else {
            FormPrintException e = new PdfProcessingException("scalePdfPages  - Invalid page size: " + pageSize);
            log.error(" scalePdfPages  - Invalid page size: " + pageSize);
            throw e;
        }
        //Create portrait and landscape rectangles for the given page size.
        Rectangle portraitPageSize;
        Rectangle landscapePageSize;
        if (dimY > dimX) {
            portraitPageSize = new Rectangle(dimX, dimY);
            landscapePageSize = new Rectangle(dimY, dimX);
        } else {
            portraitPageSize = new Rectangle(dimY, dimX);
            landscapePageSize = new Rectangle(dimX, dimY);
        }

        // Remove the document rotation before resizing the document.
        byte[] output = removeRotation(pdfOutput);
        PdfReader currentReader = null;
        try {
            currentReader = new PdfReader(output);
        } catch (IOException ex) {
            FormPrintException e = new PdfProcessingException("scalePdfPages  - Failed to create a PDF Reader");
            log.error(" scalePdfPages  - Failed to create a PDF Reader ");
            throw e;
        }

        OutputStream baos = new ByteArrayOutputStream();
        Rectangle newSize = new Rectangle(dimX, dimY);
        Document document = new Document(newSize, 0, 0, 0, 0);
        PdfWriter writer = null;
        try {
            writer = PdfWriter.getInstance(document, baos);
        } catch (DocumentException ex) {
            FormPrintException e = new PdfProcessingException("scalePdfPages  - Failed to create a PDF Writer");
            log.error(" scalePdfPages  - Failed to create a PDF Writer ");
            throw e;
        }
        document.open();
        PdfContentByte cb = writer.getDirectContent();
        PdfImportedPage page;
        float offsetX, offsetY;
        for (int i = 1; i <= currentReader.getNumberOfPages(); i++) {
            Rectangle currentSize = currentReader.getPageSizeWithRotation(i);
            if (currentReader.getPageRotation(i) != 0) {
                FormPrintException e = new PdfProcessingException("Page Rotation, " + currentReader.getPageRotation(i) + ", must be removed to re-scale the form.");
                log.error(" Page Rotation, " + currentReader.getPageRotation(i) + ", must be removed to re-scale the form. ");
                throw e;
            }
            //Reset the page size for each page because there may be a mix of sizes in the document.
            float currentWidth = currentSize.getWidth();
            float currentHeight = currentSize.getHeight();
            if (currentWidth > currentHeight) {
                newSize = landscapePageSize;
            } else {
                newSize = portraitPageSize;
            }
            document.setPageSize(newSize);
            document.newPage();
            float factorX = newSize.getWidth() / currentSize.getWidth();
            float factorY = newSize.getHeight() / currentSize.getHeight();
            // Use noEnlarge if the new page size is larger than the old one
            // and the pages should be centered instead of enlarged.
            if (noEnlarge) {
                if (factorX > 1) {
                    factorX = 1;
                }
                if (factorY > 1) {
                    factorY = 1;
                }
            }
            if (preserveAspectRatio) {
                factorX = Math.min(factorX, factorY);
                factorY = factorX;
            }
            offsetX = (newSize.getWidth() - (currentSize.getWidth() * factorX)) / 2f;
            offsetY = (newSize.getHeight() - (currentSize.getHeight() * factorY)) / 2f;
            page = writer.getImportedPage(currentReader, i);
            cb.addTemplate(page, factorX, 0, 0, factorY, offsetX, offsetY);
        }
        document.close();
        return ((ByteArrayOutputStream) baos).toByteArray();
    }

    /**
     * Remove the rotation from the pdfOutput document pages.
     */
    private static byte[] removeRotation(byte[] pdfOutput) throws FormPrintException {
        PdfReader currentReader = null;
        try {
            currentReader = new PdfReader(pdfOutput);
        } catch (IOException ex) {
            FormPrintException e = new PdfProcessingException("Remove PDF Page Rotation  - Failed to create a PDF Reader");
            log.error(" Remove PDF Page Rotation  - Failed to create a PDF Reader ");
            throw e;
        }
        boolean needed = false;
        for (int i = 1; i <= currentReader.getNumberOfPages(); i++) {
            if (currentReader.getPageRotation(i) != 0) {
                needed = true;
            }
        }
        if (!needed) {
            return pdfOutput;
        }

        OutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter writer = null;
        try {
            writer = PdfWriter.getInstance(document, baos);
        } catch (DocumentException ex) {
            FormPrintException e = new PdfProcessingException("Remove PDF Page Rotation  - Failed to create a PDF Writer");
            log.error(" Remove PDF Page Rotation  - Failed to create a PDF Writer ");
            throw e;
        }
        PdfContentByte cb = null;
        PdfImportedPage page;
        for (int i = 1; i <= currentReader.getNumberOfPages(); i++) {
            Rectangle currentSize = currentReader.getPageSizeWithRotation(i);
            currentSize = new Rectangle(currentSize.getWidth(), currentSize.getHeight()); // strip rotation
            document.setPageSize(currentSize);
            if (cb == null) {
                document.open();
                cb = writer.getDirectContent();
            } else {
                document.newPage();
            }
            int rotation = currentReader.getPageRotation(i);
            page = writer.getImportedPage(currentReader, i);
            float a, b, c, d, e, f;
            if (rotation == 0) {
                a = 1;
                b = 0;
                c = 0;
                d = 1;
                e = 0;
                f = 0;
            } else if (rotation == 90) {
                a = 0;
                b = -1;
                c = 1;
                d = 0;
                e = 0;
                f = currentSize.getHeight();
            } else if (rotation == 180) {
                a = -1;
                b = 0;
                c = 0;
                d = -1;
                e = currentSize.getWidth();
                f = currentSize.getHeight();
            } else if (rotation == 270) {
                a = 0;
                b = 1;
                c = -1;
                d = 0;
                e = currentSize.getWidth();
                f = 0;
            } else {
                FormPrintException ex = new PdfProcessingException("Remove PDF Page Rotation - Unparsable rotation value: " + rotation);
                log.error(" Remove PDF Page Rotation - Unparsable form rotation value: " + rotation);
                throw ex;
            }
            cb.addTemplate(page, a, b, c, d, e, f);
        }
        document.close();
        return ((ByteArrayOutputStream) baos).toByteArray();
    }
}
