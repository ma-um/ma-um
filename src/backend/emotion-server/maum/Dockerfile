FROM python:3.8
# 컨테이너 내에서 코드가 실행될 경로 설정
WORKDIR /usr/src/emotion
# requirements.txt에 명시된 필요한 packages 설치
COPY requirements.txt ./
RUN pip install --upgrade pip
RUN pip install -r requirements.txt
# Project를 /usr/src/app으로 복사
COPY . .
# 포트 설정
EXPOSE 8084

CMD ["python3", "manage.py", "runserver", "0.0.0.0:8084"]