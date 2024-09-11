package com.it.rabo.lostandfound.processor;

import com.it.rabo.lostandfound.entity.LostFound;
import com.it.rabo.lostandfound.util.FileUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class LostAndFoundProcessorTest {

    @InjectMocks
    private LostAndFoundProcessor victim;
    @Test
    void testProcess() throws IOException {
        File uploadedFile = FileUtil.getFileFromResource("LostItems.pdf");
        List<LostFound>  lostFoundList =  victim.process(uploadedFile);
        assert  lostFoundList.size() == 4;
    }
}
