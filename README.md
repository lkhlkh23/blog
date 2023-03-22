# 블로그 검색 API 개발
### 목차
- [빌드 및 실행](#빌드-및-실행)
- [요구사항](#요구사항)
- [개발내역](#개발내역)



### 빌드 및 실행
```
$ git clone https://github.com/lkhlkh23/blog.git
$ cd blog
$ ./gradlew clean build
$ java -jar blog-api/build/libs/blog-api.jar
```
- base uri : http://localhost:8080
- swagger uri : http://localhost:8080/swagger-ui/index.html

### 요구사항
- 블로그 검색
    - 키워드를 통해 블로그 검색 가능하고, 정홛도순 또는 최신순으로 정렬할 수 있는 기능 제공
    - 검색결과는 Paging 형태로 전달
    - 검색엔진은 카카오 (1순위) 이고, 네이버 (2순위) 이며, 검색엔진이 계속 추가될 수 있음을 고려 필요
    - 검색 완료 후, 검색어 저장 기록
- 인기검색어 목록 제공
    - 사용자들이 많이 검색한 순서대로 최대 10개까지 제공
    - 검색어 별로 검색된 횟수 표기 필요
- 멀티 모듈
- 대용량 트래픽과 데이터를 가정으로 동시성 이슈 고려 필요

### 개발내역
- 블로그 검색
    - 기능
      - 카카오 (1순위), 네이버 (2순위) 검색엔진을 이용해서 정확도/최신순으로 정렬해서 페이징 처리하여 데이터 제공 (검색엔진 추가 고려)
      - API Key 암호화
      - AOP 이용해서 비지니스 로직 (블로그 검색) 과 부수적인 기능 (검색어 저장) 분리해서 처리
      - search-api 로 검색과 관련된 로직 모듈 분리
    - request url : http://localhost:8080/kakao/v1/search?query=%EA%B8%B0%ED%98%84&sort=ACCURACY&page=1&size=10
    - response
```
{
  "data": {
    "searches": [
      {
        "title": "string",
        "contents": "string",
        "url": "string"
      }
    ],
    "page": {
      "total": 0,
      "requestPage": 0,
      "requestSize": 0,
      "last": true
    }
  },
  "status": 0,
  "message": "string"
}
```
- 인기검색어 목록 제공
    - 기능
        - 대용량 트래픽 및 대용량 데이터라는 전제에서 빠른속도로 인기검색어 전달 (redis lettuce 이용)
    - request url : http://localhost:8080/kakao/v1/keywords?limit=5
    - response
```
{
  "data": {
    "keywords": [
      {
        "keyword": "기현",
        "total": 1
      }
    ]
  },
  "status": 200,
  "message": ""
}
```

