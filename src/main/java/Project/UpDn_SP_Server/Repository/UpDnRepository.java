package Project.UpDn_SP_Server.Repository;

import Project.UpDn_SP_Server.Domain.article_data;
import Project.UpDn_SP_Server.Repository.Mapping.SummaryMapping;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

//스프링 부트에서 연결된 Mysql과의 Read 작업을 수행하기 위한 JPA Interface
public interface UpDnRepository extends JpaRepository<article_data, Long> {

    // SELECT articlesummary FROM article_data WHERE publicationdate = nowDate AND articlesummary LIKE '%' + keyword + '%'; 쿼리문을 JPA를 사용하여 실행
    List<SummaryMapping> findByPublicationDateAndArticleSummaryLike(LocalDate nowDate, String keyword, Pageable pageable);

    List<SummaryMapping> findByPublicationDateBetweenAndArticleSummaryLike(LocalDate startDate, LocalDate endDate, String keyword, Pageable pageable);
}
