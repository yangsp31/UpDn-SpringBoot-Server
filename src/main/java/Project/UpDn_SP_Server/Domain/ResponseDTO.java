package Project.UpDn_SP_Server.Domain;

import Project.UpDn_SP_Server.Repository.Mapping.EnvironmentMapping;
import Project.UpDn_SP_Server.Repository.Mapping.TitleMapping;
import lombok.Getter;

import java.util.List;

@Getter
public class ResponseDTO {
    private String Response;
    private List<TitleMapping> Company;
    private List<EnvironmentMapping> Environment;

    public ResponseDTO(String response) {
        this.Response = response;
    }

    public void setCompany(List<TitleMapping> company) {
        this.Company = company;
    }

    public void setEnvironment(List<EnvironmentMapping> environment) {
        this.Environment = environment;
    }
}
