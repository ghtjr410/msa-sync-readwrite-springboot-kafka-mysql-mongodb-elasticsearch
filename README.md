# MSA-SYNC-READWRITE-SPRINGBOOT-KAFKA-MYSQL-MONGODB-ELASTICSEARCH

# 프로젝트 목적

## MSA 환경에서 쓰기와 읽기를 분리해서 서버가 데이터를 처리하는것을 각 DB 특성을 이용해서 더 효율적으로 관리하기 위함


## 개발 순서
1. Springboot : Post 서버 초기 생성
2. Springboot : Post - RDS 연결 후 블로그 게시글 데이터 형식에 맞게 쓰기,수정,삭제를 구현
------------------------------------------------------------------------------------------
3. Post Service에서 uuid와 nickname을 어떻게 전달받을 것인지
    1. React에서 전송하는 Token 값? -> API Gateway에서 커스텀 Header에 담아서 보내야댐
    2. API Gateway에서 요청? 이건 절대아닐듯

3. Springboot : Sync 서버 초기 생성
4. Kafka : Post 서버와 Sync 서버 Kafka 적용
5. Springboot : Sync서버로 전달된 데이터를 MongoDB 데이터형식에 맞게 저장
6. SpringBoot : PostQuery 서버 초기 생성
7. SpringBoot : PostQuery - MongoDB 연결후 get으로 Query에 맞는 데이터 읽기