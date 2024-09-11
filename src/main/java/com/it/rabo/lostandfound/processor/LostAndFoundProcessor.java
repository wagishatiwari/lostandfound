package com.it.rabo.lostandfound.processor;

import com.it.rabo.lostandfound.entity.LostFound;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class LostAndFoundProcessor {

    public List<LostFound> process(File pdfFile) {

        try (PDDocument document = Loader.loadPDF(pdfFile)) {
            return extractItemDetails(getTextFromPdf(document));

        } catch (IOException e) {
            throw new RuntimeException("Error occurred while extracting data from PDF file", e);
        }
    }

    private static String getTextFromPdf(PDDocument document) throws IOException {
        PDFTextStripper pdfStripper = new PDFTextStripper();
        return pdfStripper.getText(document);
    }

    private List<LostFound> extractItemDetails(String text) {
        Pattern itemPattern = Pattern.compile("ItemName:\\s*(\\w+).*?Quantity:\\s*(\\d+).*?Place:\\s*(\\w+)", Pattern.DOTALL);
        Matcher matcher = itemPattern.matcher(text);

        return Stream.generate(() -> matcher)
                .takeWhile(Matcher::find)
                .map(m -> new LostFound(m.group(1), Integer.parseInt(m.group(2)), m.group(3)))
                .collect(Collectors.toList());
    }
}
