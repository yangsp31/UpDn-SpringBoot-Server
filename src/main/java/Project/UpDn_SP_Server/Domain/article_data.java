package Project.UpDn_SP_Server.Domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;

// 스프링부트에서 Mysql과 연결하여 JPA를 사용하기 위한 엔티티
@Entity
@Getter
public class article_data {

    // 실제 Mysql 테이블 내에 선언 되어있는 각 컬럼들과 매핑
    @Id
    @Column(name = "data_index")
    private long dataIndex;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @Column(name = "article_summary")
    private String articleSummary;

    @Column(name = "url")
    private String url;

    @Column(name = "category")
    private String category;

    public article_data() {}

    @Builder
    public article_data(long dataIndex, LocalDate publicationDate, String articleSummary, String category, String url) {
        this.dataIndex = dataIndex;
        this.publicationDate = publicationDate;
        this.articleSummary = articleSummary;
        this.category = category;
        this.url = url;
    }
}
