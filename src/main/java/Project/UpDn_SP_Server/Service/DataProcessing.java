package Project.UpDn_SP_Server.Service;

import Project.UpDn_SP_Server.Domain.ResponseDTO;
import Project.UpDn_SP_Server.Repository.Mapping.EnvironmentMapping;
import Project.UpDn_SP_Server.Repository.Mapping.TitleMapping;
import Project.UpDn_SP_Server.Repository.UpDnRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class DataProcessing {
    private final UpDnRepository updnRepository;
    private final UseGPT useGPT;

    public DataProcessing(UpDnRepository updnRepository, UseGPT useGPT) {
        this.updnRepository = updnRepository;
        this.useGPT = useGPT;
    }

    public ResponseDTO DataProcess (String keyword) {
        String AllTitle;
        LocalDate NowDate = LocalDate.now();
        long Count = 0;

        List<TitleMapping> Titles = updnRepository.findByPublicationDateAndArticleTitleLike(NowDate, "%" + keyword + "%");

        if(Titles.isEmpty()) {
            while (Titles.isEmpty() && Count < 7) {
                Titles = updnRepository.findByPublicationDateAndArticleTitleLike(NowDate.minusDays(++Count), "%" + keyword + "%");
            }

            if(Titles.isEmpty()) {
                return new ResponseDTO("Empty");
            }
        }

        List<EnvironmentMapping> Environments = updnRepository.findByPublicationDateAndCategory(NowDate, "SC");

        if(Environments.isEmpty()) {
            AllTitle = Titles.stream().map(TitleMapping::getArticleTitle).collect(Collectors.joining(", "));
            AllTitle = AllTitle + "이걸로 문장 만들어줘";

            ResponseDTO responseDTO = new ResponseDTO(useGPT.GptResponse(AllTitle));
            responseDTO.setCompany(Titles);

            return responseDTO;
        }
        else {
            AllTitle = Titles.stream().map(TitleMapping::getArticleTitle).collect(Collectors.joining(", "));
            AllTitle = AllTitle + "/" + Environments.stream().map(EnvironmentMapping::getArticleTitle).collect(Collectors.joining(", "));
            AllTitle = AllTitle + "이걸로 문장 만들어줘";

            ResponseDTO responseDTO = new ResponseDTO(useGPT.GptResponse(AllTitle));
            responseDTO.setCompany(Titles);
            responseDTO.setEnvironment(Environments);

            //System.out.println(responseDTO.getResponse());

            return responseDTO;
        }
    }
}
