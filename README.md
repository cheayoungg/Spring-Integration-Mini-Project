# Spring-Integration-Mini-Project

학습 목표

✅ REST 연동

✅ 시스템 간 인터페이스

✅ DTO Mapping

✅ JSON → XML

✅ Adapter 개념

✅ Integration Service

✅ Kafka 연계


# ERP Integration Platform

> **Shopping Service → Integration Service → Mock SAP(ERP)**
> Spring Boot + Kafka 기반의 시스템 간 데이터 연계(Integration) 미니 프로젝트

Integration 개발자(Integration Specialist) 직무를 이해하기 위해 직접 설계·구현한 프로젝트입니다.
단순 CRUD를 넘어, **주문 시스템과 ERP 간 인터페이스 설계, 이벤트 기반 비동기 처리, 데이터 포맷 변환(JSON ↔ XML), Adapter 패턴**까지 실제 기업의 Integration 업무 흐름을 축소 구현했습니다.

---

## 📌 프로젝트 목표

| 목표 | 내용 |
|---|---|
| 시스템 간 인터페이스 | 주문 시스템 ↔ ERP 간 데이터 연동 구조 설계 |
| 이벤트 기반 아키텍처 | Kafka를 활용한 비동기 메시지 처리 |
| Integration Layer | 검증(Validation), DTO 매핑, 로깅, 예외 처리, 재시도(Retry) |
| 데이터 포맷 변환 | JSON ↔ XML 변환 (Jackson XML) |
| Adapter 패턴 | SAP 연동을 추상화한 `SapAdapter` 구현 |
| 운영 환경 구성 | Docker Compose 기반 로컬 인프라 구성 |

---

## 🏗 아키텍처

```
                Client
                  │
            POST /orders
                  │
                  ▼
         Shopping Service
    (주문 생성 + Kafka Publish)
                  │
            OrderCreated
                  │
                  ▼
           Kafka Broker
                  │
                  ▼
       Integration Service
 ─────────────────────────────────
   Validation → Logging → DTO Mapping
   → JSON → XML → Retry / Error Handling
   → SAP Adapter
 ─────────────────────────────────
                  │
             REST (XML)
                  │
                  ▼
           Mock SAP ERP
             (주문 저장)
                  │
           Response (XML)
                  │
                  ▼
       Integration Service
        XML → JSON / 상태 업데이트
```

---

## 🛠 기술 스택

- **Backend** : Java, Spring Boot
- **Messaging** : Apache Kafka (KRaft mode)
- **Database** : PostgreSQL
- **Cache** : Redis
- **Data Format** : JSON, XML (Jackson XML)
- **Infra** : Docker, Docker Compose
- **Monitoring** : Kafka UI

---

## 📂 프로젝트 구조

```
integration-project/
│
├── shopping-service/      # 주문 생성 및 Kafka 이벤트 발행
├── integration-service/   # 검증·매핑·변환·SAP 연계 (핵심 서비스)
├── mock-sap/              # SAP ERP를 시뮬레이션하는 Mock 서버
├── docker-compose.yml
├── .env
└── README.md
```

### integration-service 내부 구조

```
controller / consumer / service / adapter / mapper
dto / config / client / util / exception
```

---

## 🔄 전체 동작 시나리오

```
① 사용자가 주문 생성 (POST /orders)
        ↓
② Shopping Service : DB 저장 + Kafka Publish (OrderCreated)
        ↓
③ Integration Service : 메시지 수신
        ↓
④ Validation → DTO Mapping → JSON → XML 변환
        ↓
⑤ SAP Adapter를 통해 Mock SAP 호출 (REST/XML)
        ↓
⑥ Mock SAP : 주문 저장 후 XML 응답
        ↓
⑦ Integration Service : XML → JSON 변환, 로그 저장, 주문 완료 처리
```

---

## 🧩 서비스별 상세

### 1️⃣ Shopping Service — 주문 생성 및 이벤트 발행

**API**

```
POST /orders
```

**Request**

```json
{
  "customerId": 1,
  "productId": 10,
  "quantity": 2
}
```

**처리 흐름**

```
Controller → Service → Repository → DB 저장 → Kafka Publish
```

**Kafka 발행 메시지 (OrderCreated)**

```json
{
  "orderId": 15,
  "customerId": 1,
  "productId": 10,
  "quantity": 2
}
```

**DB 스키마 (`orders`)**

| 컬럼 | 설명 |
|---|---|
| id (PK) | 주문 번호 |
| customer_id | 고객 ID |
| product_id | 상품 ID |
| quantity | 수량 |
| status | 주문 상태 |

---

### 2️⃣ Integration Service — 검증·변환·연계 (핵심)

Kafka로부터 `OrderCreated` 이벤트를 수신해 아래 파이프라인을 수행합니다.

```
Consumer → Validation → DTO Mapping → JSON → XML → SAP 호출 → 응답 처리 → Logging
```

**Validation**

- `customerId` 없음 → 예외
- `productId` 없음 → 예외
- `quantity <= 0` → 예외

**DTO Mapping (쇼핑몰 DTO → SAP DTO)**

| OrderEvent | SapOrderRequest |
|---|---|
| customerId | Customer |
| productId | Material |
| quantity | Qty |

**JSON → XML 변환 결과**

```xml
<Order>
    <Customer>1</Customer>
    <Product>10</Product>
    <Qty>2</Qty>
</Order>
```

**SAP Adapter**

실무의 webMethods Adapter, SAP Adapter, JDBC Adapter 개념을 참고해
XML 전달 → REST 호출 → XML 수신 역할을 추상화한 `SapAdapter`를 구현했습니다.

**Retry 정책**

```
SAP 장애(500 Error) → Retry 3회 → 실패 시 DLQ 처리
```

**Logging**

```
Order Received → Kafka Message → XML Request → SAP Response → Completed
```

---

### 3️⃣ Mock SAP — ERP 시뮬레이터

**API**

```
POST /sap/orders
```

**Request (XML)**

```xml
<Order>
    <Customer>1</Customer>
    <Product>10</Product>
    <Qty>2</Qty>
</Order>
```

**Response (XML)**

```xml
<Response>
    <Status>SUCCESS</Status>
    <SapOrder>500001</SapOrder>
</Response>
```

수신한 주문은 `sap_orders` 테이블에 저장됩니다.

---

## 🚀 실행 방법

### 1. 인프라 실행 (PostgreSQL, Kafka, Kafka UI, Redis)

```bash
docker compose up -d
```

**.env**

```
POSTGRES_DB=integration
POSTGRES_USER=admin
POSTGRES_PASSWORD=1234
```

### 2. 실행 확인

```bash
# 컨테이너 확인
docker ps
# integration-postgres / integration-kafka / integration-kafka-ui / integration-redis

# Kafka UI 접속
http://localhost:8080

# PostgreSQL 접속
docker exec -it integration-postgres psql -U admin

# Kafka 컨테이너 접속
docker exec -it integration-kafka bash
```

### 3. 주문 생성 테스트

```bash
curl -X POST http://localhost:{port}/orders \
  -H "Content-Type: application/json" \
  -d '{"customerId": 1, "productId": 10, "quantity": 2}'
```

---

## 🗺 개발 단계

- [x] **1단계** : Docker 환경 구성 (PostgreSQL, Kafka, Kafka UI, Redis)
- [x] **2단계** : Shopping Service 개발 및 DB 저장 확인
- [x] **3단계** : Mock SAP 개발 및 XML 요청/응답 확인
- [x] **4단계** : Integration Service 개발 및 Kafka 연동
- [x] **5단계** : Docker Compose로 전체 서비스 실행

---

## 🔮 향후 확장 계획

- [ ] **Outbox Pattern** 적용으로 Kafka 발행 신뢰성 확보
- [ ] **Dead Letter Queue(DLQ)** 및 재처리 API 구현
- [ ] **Correlation ID** 기반 요청 추적 및 구조화된 로그
- [ ] **API Gateway + JWT 인증** 추가
- [ ] **Circuit Breaker / Retry 정책** 고도화
- [ ] **Helm Chart** 기반 Kubernetes 배포 자동화

---

## 💡 이 프로젝트에서 얻은 것

- 시스템 간 데이터 연계에서 **Integration Layer가 담당하는 책임**(검증·변환·라우팅·예외 처리)의 이해
- 이기종 시스템 간 통신에서 **데이터 포맷 변환(JSON ↔ XML)** 과 **Adapter 추상화**의 필요성 체감
- Kafka 기반 비동기 연계에서 **메시지 신뢰성(재시도, DLQ)** 을 설계 단계부터 고려하는 관점
