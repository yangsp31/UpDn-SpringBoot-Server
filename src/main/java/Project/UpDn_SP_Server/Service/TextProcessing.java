package Project.UpDn_SP_Server.Service;

import Project.UpDn_SP_Server.Domain.SummaryDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextProcessing {

    public SummaryDTO TextProcess(String result) {
        String[] elements = result.split("/");

        SummaryDTO summaryDTO = new SummaryDTO();

        summaryDTO.setResult(elements[0]);
        List<String> reasons = new ArrayList<>(Arrays.asList(elements).subList(1, elements.length));

        summaryDTO.setReason(reasons);

        return summaryDTO;
    }

    public SummaryDTO NoneData() {
        SummaryDTO summaryDTO = new SummaryDTO();

        summaryDTO.setResult("Nodata");

        return summaryDTO;
    }
}
