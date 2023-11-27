package Project.UpDn_SP_Server.Repository;

import Project.UpDn_SP_Server.Domain.article_data;
import Project.UpDn_SP_Server.Repository.Mapping.SummaryMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface UpDnRepository extends JpaRepository<article_data, Long> {
    List<SummaryMapping> findByPublicationDateAndArticleSummaryLike(LocalDate nowDate, String keyword);
}
