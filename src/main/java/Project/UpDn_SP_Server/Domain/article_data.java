package Project.UpDn_SP_Server.Domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;

@Entity
@Getter
public class article_data {

    @Id
    @Column(name = "data_index")
    private long dataIndex;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @Column(name = "article_title")
    private String articleTitle;

    @Column(name = "category")
    private String category;

    @Column(name = "url")
    private String url;

    public article_data() {}

    @Builder
    public article_data(long dataIndex, LocalDate publicationDate, String articleTitle, String category, String url) {
        this.dataIndex = dataIndex;
        this.publicationDate = publicationDate;
        this.articleTitle = articleTitle;
        this.category = category;
        this.url = url;
    }
}
