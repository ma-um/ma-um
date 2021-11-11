FROM soulplay95/django:latest
RUN pip3 install django
WORKDIR /usr/src/app
COPY . .
WORKDIR ./project
CMD ["python3", "manage.py", "runserver", "0.0.0.0:8084"]
EXPOSE 8084