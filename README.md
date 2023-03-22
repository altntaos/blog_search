# blog_search
Spring WebFlux 기반의 블로그 검색 API, Kakao, Naver Open API를 활용한다.
## Required Environments
* Java 17+
* Redis
## 모듈 구성
### api
내부 로직을 담당하는 모듈, 검색어 랭킹을 저장하고 조회하는 기능을 제공한다.
### external
외부 API 연동을 담당하는 모듈, Kakao Naver Open API와 연동하여 블로그 검색결과를 제공한다.
## 모듈별 환경 설정
### api
```yaml
# Set server port
server:
  port: 8081
spring:
  data:
    redis:
      # Set Redis host, port. You can change connection info you have.
      host: localhost
      port: 6379
      #Use H2 in memory
  r2dbc:
    url: r2dbc:h2:mem:///blog_search_db
    username: sa
    password:
```
### external
```yaml
 # Set server port
server:
  port: 8080 #change if using this port
 # Set Api server base url, you can change the port what you set only.
api:
  base-url: http://localhost:8081/api 

```
## Endpoints
### api 모듈
* 검색 순위 조회
    * method : GET
    * path : /api/keyword/ranks

Request\
쿼리스트링 형식으로 전달합니다

|파라미터|타입|  필수  | 설명                                    |
|---|---|:----:|---------------------------------------|
|size|Integer|  X   | 검색 순위 리스트 사이즈 1~10 사이의 값 default : 10 |

Response
```json
[
    {
        "keyword": "국내 벛꽃여행지 추천",
        "score": 8
    },
    {
        "keyword": "통영숙소",
        "score": 5
    } 
]

```
### external 모듈
* 블로그 검색
    * method : GET
    * path : /external/search

Request\
쿼리 스트링 형태로 전달합니다.

| 파라미터  |  타입  |  필수  | 설명                                        |
|:-----:|:----:|:----:|-------------------------------------------|
|query|string|Y| 블로그 검색어                                   |
|sort|string|X| 정렬조건 정확도 순(ACC), 최신 순(RC) default : 정확도 순 |
|page|Integer|X| 결과 페이지 번호, 1~50 사이의 값 default : 1         |
|size|Integer|X| 한페이지에 볼 문서 수, 1~50 사이의 값 default: 10|

Response
```json
    "source": "KAKAO",
    "totalCount": 800,
    "contents":
      [
        {
            "title": "QCY <b>H2</b> 가성비 헤드셋 구매 / app EQ설정",
            "contents": "에어팟 프로 사용하며 만성 외이도염으로 고생하다 가성비 헤드셋 QCY <b>H2</b> 블랙을 구매하였습니다. 쿠팡 16,730원 구매 (알리에서 만원 초반대에도 구매가능하다함) 애당초 큰 머리와 너무 유행을 따라가는 모습일 것 같아 굳건하게 버티며 에어팟 프로를 사용하고 있었습니다. 이전에 남긴 리뷰를 보니 사용한지 2...",
            "url": "http://tofu-unni.tistory.com/545",
            "blogname": "두부언니",
            "thumbnail": "https://search2.kakaocdn.net/argon/130x130_85_c/F4OVPMv5eSG",
            "datetime": "2023-03-22T11:52:29.000+09:00"
        },
        {
            "title": "[JPA] JPA 기초설정과 <b>H2</b> DB 연동",
            "contents": "dependency&gt; &lt;groupId&gt;org.hibernate&lt;/groupId&gt; &lt;artifactId&gt;hibernate-entitymanager&lt;/artifactId&gt; &lt;version&gt;5.3.10.Final&lt;/version&gt; &lt;/dependency&gt; &lt;!-- <b>H2</b> 데이터베이스 --&gt; &lt;dependency&gt; &lt;groupId&gt;com.h2database&lt;/groupId&gt; &lt;artifactId&gt;<b>h2</b>&lt;/artifactId&gt; &lt;version&gt;2.1.214&lt;/version&gt; &lt;/dependency&gt; &lt;/dependencies...",
            "url": "http://prao.tistory.com/98",
            "blogname": "라오의 개발노트",
            "thumbnail": "https://search3.kakaocdn.net/argon/130x130_85_c/1GSnqM3YLCg",
            "datetime": "2023-03-16T11:30:58.000+09:00"
        }
      ]
```

## 실행
* 레디스가 반드시 설치되어 있어야 합니다.
  * 설치된 레디스 정보를 api/aplication.yml 에 작성합니다.
* 설정 파일을 변경한 경우
  * root 프로젝트의 gradle multipleBootJar Task를 실행 하면 /build 경로에 새로운 설정이 적용된 모듈별 jar가 생성됩니다.
  * jar가 있는 경로에서 java -jar 명령어를 통해 실행 시켜줍니다.
```shell
cd 'jar가 있는 경로'
java -jar api-0.0.1-SNAPSHOT.jar
java -jar external-0.0.1-SNAPSHOT.jar
```

* api 모듈 가동 시 아래와 같은 기본적인 테스트 데이터가 적재됩니다.
```json
[
    {
        "keyword": "국내 벛꽃여행지 추천",
        "score": 8
    },
    {
        "keyword": "통영숙소",
        "score": 5
    },
    {
        "keyword": "자동차",
        "score": 4
    },
    {
        "keyword": "계란찜 만들기",
        "score": 2
    },
    {
        "keyword": "정자역 맛집 추천",
        "score": 1
    }
]
```
## 파일 다운로드경로
* https://drive.google.com/file/d/1-rlwcVJLiA5VY3kwq5V-Jl5RhzkK5Xxv/view?usp=sharing
* https://drive.google.com/file/d/18PLRI9ugkBhMPwS64-OpDFwfRY7pGvtM/view?usp=sharing

