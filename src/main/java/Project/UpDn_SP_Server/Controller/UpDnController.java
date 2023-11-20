package Project.UpDn_SP_Server.Controller;

import Project.UpDn_SP_Server.Domain.ResponseDTO;
import Project.UpDn_SP_Server.Service.DataProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpDnController {
    private final DataProcessing dataProcessing;

    @Autowired
    public UpDnController(DataProcessing dataProcessing) {
        this.dataProcessing = dataProcessing;
    }

    @GetMapping("/UpDn/SP")
    public ResponseDTO gptExpectation(@RequestParam(name = "keyWord") String keyword) {
        return dataProcessing.DataProcess(keyword);
    }
}
