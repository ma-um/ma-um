## :purple_heart: 마음 :purple_heart:

> **일기 기반 음악 추천 서비스**

<br>

## :memo: ​About

- **개발 기간** : 2021.10.12 ~ 2021.11.26 (7주)
- **Track** : 자율
- **서비스명** : 마음
- **슬로건** : 하루의 마무리를 찰떡같은 음악으로 장식
- **기획 배경 & 목표**
  - 코로나 블루의 원인을 감정을 털어낼 수 있는 기회가 많이 사라졌기 때문이라고 판단.
  - 집에서도 일기 작성을 통해 감정 해소 욕구를 충족시키고 자연어 처리 기술(텍스트 감정 추출)을 활용하여 일기에 묻어난 감정을 캐치하여 제공한다.
  - 감정과 사용자의 취향을 바탕으로 음악을 추천해준다. 
  - 일기의 효능과 음악의 효과를 근거로 사용자는 일기를 쓰는 행위만으로 심리적 안정과 스트레스 해소를 느낄 수 있다.
- **서비스 특징**
  - 감성을 자극하는 디자인으로 사용자의 일기 작성 유도 + 본인만의 비밀스러운 작업 공간을 꾸미는 듯한 느낌을 줘 서비스에 애정을 갖게 함.
  - KoBERT 모델을 활용하여 감정 추출을 하고 대화형 텍스트 뿐만 아니라 일기에 최적화된 모델로 학습시킨다.
  - 가치있는 통계 정보를 제공하여 사용자로부터 보다 나은 음악 컨텐츠를 소비할 수 있게 도와준다.
  - 기존의 일기가 가지고 있던 private한 특성을 깨고 다른 사용자와 일기를 공유, 서로에게 음악을 추천하는 커뮤니티를 제공

<br>

## :office: Architecture

ver.1

![image](/uploads/88013489875bdccd9474d68923c85431/image.png)

<br>

## :wrench: ​Tech Stack

<br>

## :mount_fuji: ​Wireframe

https://www.figma.com/file/TCxtH4yewUDNDvMt2XFnZa/마음-와이어-프레임?node-id=0%3A1

<br>

## :open_file_folder: ​ERD

https://www.erdcloud.com/d/JNCBKA983wkG2D8t7

<br>

## :fire: ​Getting Started

<br>

## :sparkles: ​People

- 전도명 : 팀장. 안드로이드
- 김민기 : BE(Spring Boot), 자연어 처리 
- 양다연 : 안드로이드, 자연어 처리
- 이현송 : BE(Spring Boot), 추천 시스템 개발
- 장진우 : 자연어 처리

<br>

## :heavy_check_mark: 진행상황
21.10.22.금
- 기획 초안 완성(차후 ERD 수정)
- 각자 맡은 역할에 대한 기술 학습 중
- 차주에 바로 배포 들어갈 예정
- BE(Spring Boot)쪽 API 개발은 바로 진행 가능
- 안드로이드, 자연어 처리, 추천 시스템 개발 쪽은 모두 처음이라 학습이 끝나는대로 개발 진행 예정

21.10.25.월

- FE - 안드로이드
  - 코틀린 언어 학습 진행중
  - 초기 세팅 해놓기
- BE - Spring Boot
  - 카카오 OAuth 인증이 android 단에서 어떻게 처리되는지 알 필요가 있음 => 로그인이 1순위
  - 기본 프로젝트 구조 설정 및 엔티티 생성 후 커밋할 예정
  - 데이터베이스 AWS(도커)에 올려야함
  - 테스트 => jacoco. 테스트 커버리지 맞추는게 쉽지않다. 비즈니스로직을 제외한 커스텀 익셉션, 엔티티 등에 대한 테스트 코드도 포함되어야 함
  - 사용자 관리 서버 기본 만들기
    - 의존성 설정
    - 엔티티 만들기
    - 엔티티 테스트
- NLP
  - KoBERT, KorBERT, HanBERT 등등 한국어에 대한 자연어 처리 모델이 여러 가지 있음
  - 현재 colab 을 사용해서 (주피터 노트북) 결과를 뽑아낸 것이라서 아나콘다 설치 후 파이썬 파일로 추출하는 과정 진행해야 할 듯
  - 긴 텍스트를 인풋으로 넣어서 나오는 결과 확인
  - ![image](/uploads/1f2634e56528872ed639d50c11a061cf/image.png)
  - KoBERT 사용해보기
  - 직접 학습할 일기 데이터셋 찾아보기, 사용할 모델 결정하기
  - 아나콘다 설치
- 추천
