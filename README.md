# UpDn - Ghat GPT를 활용한 기업의 주가 등락 예측 서비스 (Back-end application)

* 2023.09.20. - 2023.12.07.
* 2인 팀 프로젝트로 진행
<br><br><br>

# 개요

* 크롤링한 경제 뉴스 데이터를 기반으로, Prompt engineering을 활용하여 Chat GPT로 해당 기업의 주가 등락을 예측하는 서비스.
* 주식 투자자의 의사결정 과정에 신뢰성 있는 정보 제공과 경제 분야 뉴스 기사 접근성을 높일 수 있는 서비스.
* 단시간 내에 가치있는 주식/경제 정보를 얻을 수 있는 플랫폼 역할 수행.
<br><br>

# Architecture
![upDnserverflow](https://github.com/user-attachments/assets/5ed78265-53ec-48bc-a6d8-33dea48b7d69)
<br><br><br>

![image](https://github.com/user-attachments/assets/3e255776-c80d-4a06-bcf7-2ff8700b3a80)
<br><br>

# ERD

![image](https://github.com/user-attachments/assets/170924d6-0bc7-4bf4-932d-f4a9cc214fb7)
<br><br>

# 사용기술

* ### SpringBoot
  * 안정적인 HTTP 통신 구현가능, 다양한 라이브러리 지원, DB 연동 처리에 필요한 백엔드 구조를 모두 만족한다고 판단하여 선택.

* ### JPA
  * JPA 쿼리 메서드를 활용한 간편한 CRUD가 가능해, 생산성 향상을 위해 선택.

* ### MySql
  * 뉴스기사 텍스트는 물론, 발행 날짜, url, 경제 뉴스 세부분야 등의 데이터를 다양한 조건으로 조회/관리하기 위해 RDBMS가 필요하다 판단하여 선택.
 
* ### openai의 gpt-4 API
  * 입력되는 텍스트를 기반으로, 특정 기업의 주가 예측 A.I 모델 구현에 필요한 기간과 작업량을 고려했을 때, 2명으로는 불가능하다 판단하여 외부 모델 활용을 선택.
<br><br>

# 주요 개발내역

* ### 뉴스기사 DB 조회 구현 ([코드위치](https://github.com/yangsp31/UpDn-SpringBoot-Server/blob/master/src/main/java/Project/UpDn_SP_Server/Service/DataProcessing.java#L36))
  * 요청으로 들어온 기업명을 Keyword로 하여 현재 날짜에 해당하는 가장 최신의 뉴스기사를 조회.
  * 페이징을 활용하여 가장 최근의 뉴스 기사가 포함된 페이지 1개(총 13개)만 조회 하도록 구성.
  * 현재 날짜 기준으로 데이터가 존재하지 않을경우, 현재 날짜를 제외한 7일간의 데이터 탐색 진행.
----
 
* ### Prompt engineering을 활용하여, Chat GPT로 기업의 주가 등락 예측 요청 구현
  * 프로젝트 요구사항에 맞는 응답을 얻기 위한 Prompt engineering 텍스트 구축.
  * chatgpt-spring-boot-starter:1.0.4 라이브러리를 활용하여 Chat GPT API 연동.
  * 조건에 맞는 뉴스기사 텍스트와 Prompt engineering 텍스트를 사용하여 예측 요청 진행.
  * 예측 텍스트를 응답으로 받아, DTO로 구축후 클라이언트에게 반환 로직 구현.

* Prompt engineering 텍스트
```
You are a cold, realistic stock analyst who utilizes all the GPT databases you have to analyze stocks objectively and rationally with the data you are given. The only data you are given are summaries of various stock news articles. All stock news summaries are the most recent ones. You must be realistic and objective when analyzing stocks. Your answer must be written in Korean only. In your answer, you must write your entire analytical opinion and the rationale for that opinion. When writing your answer, you must separate your entire analysis and the rationale for your opinion with a '/'. For example, you must write something like this Expected to rise/ Market capitalization is rising. The full analysis opinion should always be written first and should only be written once. Also, the full analysis opinion should only be written from a rising or falling perspective. Under no circumstances should the full analysis opinion be written as a conclusion. When writing reasons, you should write no more than five, and each reason should be separated by a '/'. You should only write conclusions after making inferences based on the data in the news article summary provided. You should also not use sentences that recommend trading the stock. All sentences should be no longer than 20 characters.
```
<br>

* Prompt engineering을 진행한 Chat GPT의 응답

![image](https://github.com/user-attachments/assets/11fbc8ec-d39c-437b-873b-61ebbbb871b3)

* Chat GPT의 응답에 대한 DTO 구조

![image](https://github.com/user-attachments/assets/968525f7-d50f-4242-9af2-9450efaccf56)
<br><br>

# 회고 & 개선 필요사항/방법 (회고 원문 : [Velog](https://velog.io/@yang_seongp31/UpDn-SpringBoot))

* ### 뉴스기사 DB 조회
  * 요청으로 받은 KeyWord가 되는 기업명을 기반으로, 뉴스 기사 요약문구에서 LIKE%'KeyWord'%로 탐색.
  * 기업에 따라 다르겠지만 특정 기업에 해당하는 뉴스 기사는 같은 시간에 수십개가 발행 될수도 있음.
  * 따라서, LIKE%'KeyWord'%로 조회하는 방식은 DB에 저장된 모든 텍스트를 탐색하는 방식이기에 굉장히 비효율적이며, 성능 또한 감소시키는 주요 요인이라 판단.
  * 페이징은 단순히 반환 데이터의 수량을 조절할 뿐이기에, 해결책이 될수 없음.
  * 예측 데이터의 부적절한 선택 및 정제로 인해 뉴스 DB 조회의 문제가 연쇄적으로 발생함. (예측 데이터의 선택 및 정제에 대한 회고는 여기서 다루지 않음)
 
* ### Flask와 응답/요청
  * 프로젝트에서 RealTimeData(USD, JYP 현재가와 현재 최다 검색 기업명 데이터)를 Flask서버에 요청 하도록 구성됨.
  * RealTimeData는 Flask서버에서 실시간으로 크롤링 하여 얻은 데이터.
  * 이 방식은 요청이 많아질 경우, 모든 요청에 대해 매번 크롤링을 수행해야 했고, 그 결과 Flask 서버에 과도한 부하가 발생할 수 있으며, 최악의 경우 모든 요청을 정상적으로 처리가 불가하다 판단.
  * RealTimeData를 1분 ~ 10분 단위로 주기적으로 수집하여, DB나 Redis에 저장한 뒤, 사용자의 요청 시 SpringBoot가 이를 직접 조회해 응답하는 구조나, 외부 API를 앱에서 직접 호출하도록 하는 방식이 적절하다 판단.
  * 위와 같은 방법들로 구축하였다면, Flask 서버 없이 배치 서버로 단순화할 수 있었고, 실질적인 요청 처리도 SpringBoot에서 모두 가능했을 것이라 생각.
  <br>
  
  * 부하테스트 예시 자료 (로컬PC 환경에서 임의로 테스트한 결과임으로 실제와 오차가 존재할수 있음.)

  ![image](https://github.com/user-attachments/assets/42a7133c-701a-4655-aa23-9b20cf4d0a4b)
  <br>

  * 테스트용 Flask 로직
  ```
  import random
  import time

  app = Flask(__name__)

  # 최다 검색 기업명과 환율을 가정한 데이터 생성
  def generate_random_companies():
      companies = ["Company A", "Company B", "Company C", "Company D", "Company E"]
      return random.choice(companies)

  def generate_random_exchange_rates():
      return {
          "USD_TO_EUR": random.uniform(0.8, 1.2),
          "USD_TO_JPY": random.uniform(100, 150),
          "USD_TO_GBP": random.uniform(0.7, 1.0),
      }

  # 부하를 발생시키는 로직
  
  def simulate_heavy_load():
      # 일부러 CPU 부하를 발생시키는 루프
      result = []
      for _ in range(10000):  # 10000번 반복하여 계산량 증가
          result.append(generate_random_companies())
          result.append(generate_random_exchange_rates())
      return result

  @app.route('/', methods=['GET'])
  def start_load():
      try:
          data = simulate_heavy_load()

          # 부하 작업이 끝난 후 결과를 반환
          return jsonify({
              "status": "success",
              "data": data[:5],
          }), 200
      except Exception as e:
          return jsonify({'error': str(e)}), 500

  if __name__ == '__main__':
      app.run(debug=True, host='0.0.0.0', port=5000, threaded=True)
  ```
<br><br>

# 개선 필요사항/방법
  * 뉴스기사 DB 조회 방식을 LIKE%'KeyWord'% 에서 다른 방식으로 탐색/전환 필요. (ex: FULLTEXT - 정확히 잘 모름. 추가 학습 필요)
  * RealTimeData를 1분 ~ 10분 단위로 주기적으로 수집하여, DB나 Redis에 저장한 뒤, 사용자의 요청 시 SpringBoot가 이를 직접 조회해 응답하는 구조나, 외부 API를 앱에서 직접 호출하도록 하는 방식으로 전환.

