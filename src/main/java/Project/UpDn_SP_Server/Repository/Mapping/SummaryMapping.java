package Project.UpDn_SP_Server.Repository.Mapping;

// JPA를 사용하여 Mysql로 쿼리 진행 결과를 반환 받을 때 특정 컬럼의 데이터만을 반환 받기 위한 Interface
public interface SummaryMapping {
    String getArticleSummary();   // Mysql 테이블에 선언되어있는 articlesummary 컬럼의 데이터만 반환 받음을 선언
}
