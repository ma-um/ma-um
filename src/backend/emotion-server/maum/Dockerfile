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
# gunicorn 실행
# CMD ["gunicorn", "--bind", "0.0.0.0:8084", "project_name.wsgi:application"]
CMD ["python3", "manage.py", "runserver", "0.0.0.0:8084"]
# EXPOSE, CMD는 docker-compose에서 작성할 것이기 때문에, 동작하는지만 확인 후 주석or삭제 하도록 하자.