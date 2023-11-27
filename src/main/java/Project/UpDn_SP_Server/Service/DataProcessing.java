package Project.UpDn_SP_Server.Service;

import Project.UpDn_SP_Server.Domain.SummaryDTO;
import Project.UpDn_SP_Server.Repository.Mapping.SummaryMapping;
import Project.UpDn_SP_Server.Repository.UpDnRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class DataProcessing {
    private final UpDnRepository updnRepository;
    private final UseGPT useGPT;
    private final TextProcessing textProcessing;

    public DataProcessing(UpDnRepository updnRepository, UseGPT useGPT, TextProcessing textProcessing) {
        this.updnRepository = updnRepository;
        this.useGPT = useGPT;
        this.textProcessing = textProcessing;
    }

    public SummaryDTO DataProcess (String keyword) {
        String AllSummary;
        LocalDate NowDate = LocalDate.now();
        String Result;
        long Count = 0;

        List<SummaryMapping> Summary = updnRepository.findByPublicationDateAndArticleSummaryLike(NowDate, "%" + keyword + "%");

        if (Summary.isEmpty()) {
            while (Summary.isEmpty() && Count < 7) {
                Summary = updnRepository.findByPublicationDateAndArticleSummaryLike(NowDate.minusDays(++Count), "%" + keyword + "%");
            }

            if (Summary.isEmpty()) {
                return textProcessing.NoneData();
            }

            AllSummary = Summary.stream().map(SummaryMapping::getArticleSummary).collect(Collectors.joining(", "));
            Result = useGPT.GptResponse(AllSummary);

            return textProcessing.TextProcess(Result);

        } else {
            AllSummary = Summary.stream().map(SummaryMapping::getArticleSummary).collect(Collectors.joining(", "));
            Result = useGPT.GptResponse(AllSummary);

            System.out.println(Result);
            return textProcessing.TextProcess(Result);
        }
    }
}
