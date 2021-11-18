![logo.png](logo.png)

## 🎈 프로젝트 이름 : 마음

## 👨‍👩‍👦‍👦  팀원소개

|NAME|ROLE|EMAIL|
|------|---|---|
|👦전도명|Distribution & Front-End|soulplay95@gmail.com|
|🧑김민기|Back-End||
|🧑장진우|NLP & Back-End||
|👦이현송|Recommendation Algorithm|lhs7615@naver.com|



## 📖 프로젝트 소개

마음은 감정분석 및 음악추천 다이어리 입니다. 일기를 쓸때 인공지능으로 자신의 감정을 보여주고 음악을 추천 해 줌 으로써 일기를 쓸때 내 마음도 돌아보고 음악도 추천 받도록 하였습니다. 이외에도 회원들은 안드로이드 앱에서 자신의 캘린더에서 감정도 볼 수 있고 기분에 맞는 음악도 추천 받을 수 있습니다. 이러한 일기 작성 프로그램으로 사용자들이 좀 더 자기만의 마음 흐름과 삶의 리듬을 찾을 수 있게 도와 줍니다. 바쁜일상에서 효율적으로 일기 쓰고 싶은 분들 저희 마음과 함께하세요!


## 🚀 배포 플로우

![배포_플로우.png](배포_플로우.png)



## 💻 기술 스택

### Frontend

- Android 11 (Red Velvet Cake)
  - 네이티브 앱과 같이 뛰어난 범용성을 제공하는 플렛폼을 구현하고자 Android를 채택하였습니다.
  

### Backend

- Spring-Boot 2.3.9
- MariaDB 10.5.9



### AI

##### Teachablemachine with google

1. **CNN** :
CNN(Convolutional Neural Network)은 이미지의 공간 정보를 유지하면서 인접 이미지와의 특징을 효과적으로 인식하고 강조하는 방식으로 이미지의 특징을 추출하는 부분과 이미지를 분류하는 부분으로 구성됩니다. 특징 추출 영역은 Filter를 사용하여 공유 파라미터 수를 최소화하면서 이미지의 특징을 찾는 Convolution 레이어와 특징을 강화하고 모으는 Pooling 레이어로 구성됩니다.
CNN은 Filter의 크기, Stride, Padding과 Pooling 크기로 출력 데이터 크기를 조절하고, 필터의 개수로 출력 데이터의 채널을 결정합니다.
CNN는 같은 레이어 크기의 Fully Connected Neural Network와 비교해 볼 때, 학습 파라미터양은 20% 규모입니다. 은닉층이 깊어질 수록 학습 파라미터의 차이는 더 벌어집니다. CNN은 Fully Connected Neural Network와 비교하여 더 작은 학습 파라미터로 더 높은 인식률을 제공합니다.

2. **YOLO** :
YOLO(You Only Look Once)는 대표적인 단일 단계 방식의 객체 탐지 알고리즘입니다. YOLO 알고리즘은 원본 이미지를 동일한 크기의 그리드로 나눕니다. 각 그리드에 대해 그리드 중앙을 중심으로 미리 정의된 형태(predefined shape)으로 지정된 경계박스의 개수를 예측하고 이를 기반으로 신뢰도를 계산합니다. 이미지에 객체가 포함되어 있는지, 또는 배경만 단독으로 있는지에 대한 여부가 포함되겠죠. 높은 객체 신뢰도를 가진 위치를 선택해 객체 카테고리를 파악합니다.



### Dev-Ops

- AWS EC2 (Ubuntu 18.0.4)
- Jenkins 2.249.2
  - CI/CD 자동화를 통해 개발 생산성을 높이기 위하여 Jenkins를 도입하였습니다.
- Docker 19.03.13
  - 배포에서의 용이성을 위하여 Docker를 도입하였습니다.
  

## 📜 기획

### [WireFrame](https://www.figma.com/file/TCxtH4yewUDNDvMt2XFnZa/%EB%A7%88%EC%9D%8C-%EC%99%80%EC%9D%B4%EC%96%B4-%ED%94%84%EB%A0%88%EC%9E%84)

### [ERD](https://www.erdcloud.com/d/JNCBKA983wkG2D8t7)




## 📱 페이지 기능 소개
### 1. 메인페이지 (to-do list를 추가 수정 삭제 할 수 있도록 도와줌)
### 2. 회원가입 (회원의 정보를 입력하여 db에 추가로 회원을 저장하게 도와줌)
### 3. 셀프스터디 (인공지능을 이용해서 실제 학생이 공부하는 시간과 다른 행동을 하는 시간을 구분해줌)
### 4. 다이어리 (일간, 주간, 월간 으로 나눠서 사람들의 공부시간을 정리 해줌)
### 5. 프로필 (회원의 정보를 보여주고 수정 할 수 있게 도와줌)



## 🔌 Contributing

### [API 명세서](https://www.notion.so/API-8a9f37d221b84a39a954a6b48cc0627c)






## ⭐  Develop Rules
### branch
```
master -> develop -> FEdevelop -> feat/기능이름
master -> develop -> BEdevelop -> feat/기능이름
```

### branch name
```
ex)

BE_register

FE_login
```

### commit 메시지

```
feat : 새로운 기능에 대한 커밋

fix : 버그 수정에 대한 커밋

build : 빌드 관련 파일 수정에 대한 커밋

chore : 그 외 자잘한 수정에 대한 커밋

ci : CI관련 설정 수정에 대한 커밋

docs : 문서 수정에 대한 커밋

style : 코드 스타일 혹은 포맷 등에 관한 커밋

refactor :  코드 리팩토링에 대한 커밋

test : 테스트 코드 수정에 대한 커밋
```
